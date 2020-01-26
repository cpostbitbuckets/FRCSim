package frc.robot.simulator.sim;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.events.EventManager;
import frc.robot.simulator.sim.field.FieldSim;
import frc.robot.simulator.sim.field.box2d.Box2DSim;
import frc.robot.simulator.sim.field.howard.HowardSim;
import frc.robot.simulator.sim.field.wheeldisplacement.WheelDisplacementSim;
import frc.robot.simulator.sim.motors.MotorSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class SimulatorThread {
    private static final Logger log = LoggerFactory.getLogger(SimulatorThread.class);

    private MotorSimulator motorSimulator;
    private Box2DSim box2DSim;
    private WheelDisplacementSim wheelDisplacementSim;
    private HowardSim howardSim;

    private final List<FieldSim> fieldSims = new ArrayList<>();

    private long lastTime;
    private boolean simulationRunning = false;

    private final ExecutorService simulationThreadExecutor = Executors.newSingleThreadExecutor();
    private Object simulatorLock = new Object();

    @Inject
    public SimulatorThread(MotorSimulator motorSimulator, Box2DSim box2DSim, HowardSim howardSim, WheelDisplacementSim wheelDisplacementSim) {
        this.motorSimulator = motorSimulator;
        this.box2DSim = box2DSim;
        this.wheelDisplacementSim = wheelDisplacementSim;
        this.howardSim = howardSim;

        // add all these sims to a list for easy iterating
        fieldSims.add(this.box2DSim);
        fieldSims.add(this.howardSim);
        fieldSims.add(this.wheelDisplacementSim);
    }

    /**
     * Start the simulation thread
     */
    public void startSimulationThread() {
        synchronized (simulationThreadExecutor) {
            if (!simulationRunning) {
                lastTime = System.currentTimeMillis();
                // our simulation updates every 10 ms, but only updates the UI every 100ms
                simulationThreadExecutor.submit(simulationThreadLoop);

                // wait for the thread to start
                while (!simulationRunning) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        log.error("Failed to sleep", e);
                    }
                }
                log.info("Motor simulation is running");
            } else {
                log.error("Simulation already running, can't start it again.");
            }
        }
    }

    /**
     * Stop the simulation thread
     */
    public void stopSimulationThread() {
        simulationRunning = false;
        simulationThreadExecutor.shutdown();
    }

    /**
     * This loop is called every period (10ms) to calculate the motor outputs
     */
    Runnable simulationThreadLoop = () -> {
        try {
            // setup the sims
            this.fieldSims.forEach(fs -> fs.create());
            // don't start right away, delay a bit
            Thread.sleep(50);

            while(true) {
                long currentTime = System.currentTimeMillis();
                // update the simulator and UI components
                long deltaTime = currentTime - lastTime;
                double deltaTimeSeconds = deltaTime / 1000.0;

                // simulate the motors and publish them
                synchronized (simulatorLock) {
                    RobotProto.MotorOutputs simMotorOutputs = motorSimulator.simulateMotors(deltaTime);
                    this.fieldSims.forEach(fs -> fs.step(deltaTimeSeconds));

                    EventManager.publish(simMotorOutputs);

                    // publish each RobotPosition
                    this.fieldSims.forEach(fs -> EventManager.publish(fs.getRobotPosition()));

                }
                lastTime = currentTime;

                if (!simulationRunning) {
                    log.info("Started motor simulation thread.");
                    simulationRunning = true;
                }

                // wait a ms between loops
                Thread.sleep(0, 999999);
            }
        } catch (Throwable e) {
            log.error("Failed to run motor server simulation thread, exiting", e);
            System.exit(1);
        }
    };

}
