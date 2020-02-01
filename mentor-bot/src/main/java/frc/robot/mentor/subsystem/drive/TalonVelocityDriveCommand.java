package frc.robot.mentor.subsystem.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.function.DoubleSupplier;

public class TalonVelocityDriveCommand extends CommandBase {

    private final BaseTalon leftMotor;
    private final BaseTalon rightMotor;
    private final int maxSpeed;
    private final DoubleSupplier speed;
    private final DoubleSupplier turn;

    public TalonVelocityDriveCommand(Subsystem subsystem, int maxSpeed, BaseTalon leftMotor, BaseTalon rightMotor, DoubleSupplier speed, DoubleSupplier turn) {
        addRequirements(subsystem);
        this.maxSpeed = maxSpeed;
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.speed = speed;
        this.turn = turn;
    }

    /**
     * While executing, continually update the differentialDrive with the new speed/turn values
     */
    @Override
    public void execute() {
        super.execute();

        double currentSpeed = speed.getAsDouble();
        double currentTurn = turn.getAsDouble();
        double leftSpeed = currentSpeed + currentTurn;
        double rightSpeed = currentSpeed - currentTurn;
        // every execute, use the velocity drive
        // go to 20k ticks per 100 ms
        leftMotor.set(ControlMode.Velocity, leftSpeed * maxSpeed);
        rightMotor.set(ControlMode.Velocity, rightSpeed * maxSpeed);
    }

}
