package frc.robot.simulator.sim.field.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.field.FieldSim;
import frc.robot.simulator.sim.motors.MotorStore;
import frc.robot.simulator.sim.motors.SimDCMotor;
import frc.robot.simulator.sim.motors.SimMotor;

/**
 * This class uses box2d physics (from libgdx) to simulate robot movement
 * Note: Turning isn't really working. The angular impulse stuff is busted
 */
@Singleton
public class Box2DSim extends FieldSim {


    private World world;
    private Field field;
    private DriveBase driveBase;

    @Inject
    public Box2DSim(MotorStore motorStore, SimulatorConfig simulatorConfig) {
        super(motorStore, simulatorConfig);
    }

    @Override
    protected RobotPosition supplyRobotPosition() {
        return new RobotPosition(RobotPosition.Type.Box2d);
    }

    /**
     * create() creates all the box2d Body's for applying physics to
     */
    @Override
    public void create() {
        super.create();
        Vector2 gravity = new Vector2(0, 0);
        world = new World(gravity, true);

        field = new Field(world, 0, 0, Field.width, Field.height);

        driveBase = new DriveBase(world,
                (float)robotPosition.x,
                (float)robotPosition.y,
                0.3145536f*2,
                0.3145536f*2);

    }

    /**
     * Update all bodies in the world
     * @param deltaTime
     */
    @Override
    public void step(double deltaTime) {

        addForces();
        // simulate physics
        world.step((float) deltaTime, 6, 2);

        // update the robot position after physics has been calculated
        updateRobotPosition();
    }

    /**
     * Update forces with motors
     */
    public void addForces() {
        for (SimMotor motor : motorStore.getSimMotorsSorted()) {
            if ((motor.isLeftDriveMotor() || motor.isRightDriveMotor()) && motor instanceof SimDCMotor) {

                SimDCMotor dcMotor = (SimDCMotor) motor;
                double wheelRadius = simulatorConfig.driveBase.wheelRadius;
                double kt = dcMotor.kt;
                double kv = dcMotor.kv;
                double resistance = dcMotor.resistance;
                double voltage = dcMotor.voltage;
                double velocity = dcMotor.velocity;
                double viscousFrictionConstant = simulatorConfig.driveBase.viscousFriction;

                // compute force using HowardMath™
                double force = kt / (resistance * wheelRadius) * voltage - ((kt * kv) / (resistance * wheelRadius) + viscousFrictionConstant / wheelRadius) * velocity;
                motor.force = force;

                switch (motor.getId()) {
                    case 1:
                        driveBase.setFrontLeftMotorOutput((float) motor.getOutput());
                        break;
                    case 2:
                        driveBase.setRearLeftMotorOutput((float) motor.getOutput());
                        break;
                    case 3:
                        driveBase.setFrontRightMotorOutput((float) motor.getOutput());
                        break;
                    case 4:
                        driveBase.setRearRightMotorOutput((float) motor.getOutput());
                        break;
                }
            }
        }

        driveBase.updateFriction();

    }

    /**
     * Update the RobotPosition object with data from the physics world
     */
    public void updateRobotPosition() {
        Transform transform = driveBase.getTransform();
        robotPosition.x = transform.getPosition().x;
        robotPosition.y = transform.getPosition().y;
        robotPosition.heading = transform.getRotation();
    }
}
