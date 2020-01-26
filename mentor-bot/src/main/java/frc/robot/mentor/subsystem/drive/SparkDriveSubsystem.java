/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.mentor.subsystem.drive;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.config.Config;
import frc.robot.mentor.subsystem.BitBucketSubsystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.DoubleSupplier;

/**
 * Add your docs here.
 */
public class SparkDriveSubsystem extends BitBucketSubsystem {
    private static final Logger log = LoggerFactory.getLogger(SparkDriveSubsystem.class);

    final CANSparkMax[] leftMotors;
    final CANSparkMax[] rightMotors;

	public SparkDriveSubsystem(Config config) {
        super(config);

        // initialize the motors from config
        leftMotors = initSparksFromSettings(config, config.sparkDriveConfig.leftMotors);
        rightMotors = initSparksFromSettings(config, config.sparkDriveConfig.rightMotors);
    }

    @Override
    public void outputTelemetry() {
        /* Used to build string throughout loop */
        for (CANSparkMax motor : allSparkMotors) {
            SmartDashboard.putNumber(getName() + "/Motor " + motor.getDeviceId() + "/out", motor.getAppliedOutput());
            SmartDashboard.putNumber(getName() + "/Motor " + motor.getDeviceId() + "/vel", motor.getEncoder().getVelocity());
            SmartDashboard.putNumber(getName() + "/Motor " + motor.getDeviceId() + "/pos", motor.getEncoder().getPosition());
        }
    }

    public Command drive(final DoubleSupplier speed, final DoubleSupplier turn) {
        return new SparkVelocityDriveCommand(this, config.velocitySlotIndex, config.sparkDriveConfig.maxVelocity, leftMotors[0], rightMotors[0], speed, turn);
	}

    public void resetSensorPosition() {
	    allSparkMotors.forEach(m -> m.getEncoder().setPosition(0));
    }

}
