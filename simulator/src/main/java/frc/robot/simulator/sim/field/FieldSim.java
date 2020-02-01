package frc.robot.simulator.sim.field;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.field.box2d.Field;
import frc.robot.simulator.sim.motors.MotorStore;

@Singleton
public abstract class FieldSim {
    protected final RobotPosition robotPosition = supplyRobotPosition();
    protected MotorStore motorStore;
    protected SimulatorConfig simulatorConfig;

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
        robotPosition.x = 181 * Field.metersPerPixelWidth - Field.width / 2;
        robotPosition.y = Field.height / 2 - 123 * Field.metersPerPixelHeight;
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
        robotPosition.x = 181 * Field.metersPerPixelWidth - Field.width / 2;
        robotPosition.y = Field.height / 2 - 123 * Field.metersPerPixelHeight;
        robotPosition.velocity = 0;
        robotPosition.angularVelocity = 0;
        robotPosition.heading = 0;
    }
}
