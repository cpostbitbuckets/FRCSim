package frc.robot.subsystem.elevator;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.config.Config;
import frc.robot.subsystem.BitBucketSubsystem;
import frc.robot.subsystem.elevator.commands.IdleCommand;
import frc.robot.subsystem.elevator.commands.MoveToPositionCommand;

public class ElevatorSubsystem extends BitBucketSubsystem {

    public WPI_TalonSRX elevatorMotor1 = new WPI_TalonSRX(config.elevatorConfig.elevatorMotor.id);
    public WPI_TalonSRX elevatorFollower = new WPI_TalonSRX(config.elevatorConfig.elevatorMotorFollower.id);

    IdleCommand idleCommand;
    MoveToPositionCommand moveToPositionCommand;

    public ElevatorSubsystem(Config config) {
        super(config);

        // the lame simulator requires these three fields for positional control
        elevatorMotor1.config_kF(0, 1023. / 20000.);
        elevatorMotor1.configMotionAcceleration(6000);
        elevatorMotor1.configMotionCruiseVelocity(20000);

        elevatorFollower.follow(this.elevatorMotor1);

        idleCommand = new IdleCommand(this);
        idleCommand.start();

        moveToPositionCommand = new MoveToPositionCommand(this);

    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(idleCommand);
    }

    public void moveToPosition(int position) {
        moveToPositionCommand.setTargetPosition(position);
        moveToPositionCommand.start();
    }

    public void idle() {
        idleCommand.start();
    }

}