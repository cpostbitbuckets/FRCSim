package frc.robot.subsystem.elevator.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystem.elevator.ElevatorSubsystem;

public class IdleCommand extends Command {

    ElevatorSubsystem subsystem;

    @Override
    protected boolean isFinished() {
        return false;
    }

    public IdleCommand(ElevatorSubsystem subsystem) {
        super(subsystem);
        this.subsystem = subsystem;
    }

    @Override
    public synchronized void start() {
        // TODO Auto-generated method stub
        super.start();

        this.subsystem.elevatorMotor1.set(ControlMode.PercentOutput, 0);
        this.subsystem.elevatorFollower.set(ControlMode.PercentOutput, 0);
    }

}