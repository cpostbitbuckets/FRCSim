package frc.robot.mentor.subsystem.drive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

import java.util.function.DoubleSupplier;

public class SparkVelocityDriveCommand extends CommandBase {

    private final CANSparkMax leftMotor;
    private final CANSparkMax rightMotor;
    private final int maxSpeed;
    private final int velocitySlotIndex;
    private final DoubleSupplier speed;
    private final DoubleSupplier turn;

    public SparkVelocityDriveCommand(Subsystem subsystem, int velocitySlotIndex, int maxSpeed, CANSparkMax leftMotor, CANSparkMax rightMotor, DoubleSupplier speed, DoubleSupplier turn) {
        addRequirements(subsystem);
        this.velocitySlotIndex = velocitySlotIndex;
        this.maxSpeed = maxSpeed;
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        this.speed = speed;
        this.turn = turn;
    }

    /**
     * While executing, continually update the motors with new speed/turn values
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
        this.leftMotor.getPIDController().setReference(leftSpeed * maxSpeed, ControlType.kVelocity, velocitySlotIndex);
        this.rightMotor.getPIDController().setReference(rightSpeed * maxSpeed, ControlType.kVelocity, velocitySlotIndex);
    }

}
