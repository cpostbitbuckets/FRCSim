package frc.robot.simulator.sim.field;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.field.wheeldisplacement.Field;
import frc.robot.simulator.sim.motors.MotorStore;

@Singleton
public abstract class FieldSim {
    protected final RobotPosition robotPosition = supplyRobotPosition();
    protected MotorStore motorStore;
    protected SimulatorConfig simulatorConfig;
    protected double startHeading = 0;

    protected abstract RobotPosition supplyRobotPosition();

    @Inject
    public FieldSim(MotorStore motorStore, SimulatorConfig simulatorConfig) {
        this.motorStore = motorStore;
        this.simulatorConfig = simulatorConfig;
    }

    /**
     * Override this for anything needed to setup a new FieldSim
     */
    public void create() {
        // set the robot position based on the field image we are using
        resetRobot();
    }

    /**
     * Simulate robot position for time
     * @param deltaTime The deltaTime for this step, in seconds
     */
    public abstract void step(double deltaTime);

    public RobotPosition getRobotPosition() {
        return robotPosition;
    }

    public void resetRobot() {
        robotPosition.x = simulatorConfig.startPosition.x;
        robotPosition.y = simulatorConfig.startPosition.y;
        robotPosition.velocity = 0;
        robotPosition.angularVelocity = 0;
        robotPosition.heading = simulatorConfig.startPosition.heading;
        startHeading = simulatorConfig.startPosition.heading;
    }
}
