package frc.robot.subsystem.elevator.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystem.elevator.ElevatorSubsystem;

public class MoveToPositionCommand extends Command {

    ElevatorSubsystem subsystem;

    int targetPosition = 0;

    @Override
    protected boolean isFinished() {
        return false;
    }

    public MoveToPositionCommand(ElevatorSubsystem subsystem) {
        super(subsystem);
        this.subsystem = subsystem;
    }

    @Override
    public synchronized void start() {
        super.start();

        this.subsystem.elevatorMotor1.set(ControlMode.MotionMagic, targetPosition);
    }

    public void setTargetPosition(int targetPosition) {
        this.targetPosition = targetPosition;
    }

}