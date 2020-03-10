/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.mentor.subsystem.drive;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import frc.robot.config.Config;
import frc.robot.config.SubsystemConfig;
import frc.robot.mentor.subsystem.BitBucketSubsystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.function.DoubleSupplier;

import static java.util.Map.entry;

/**
 * Add your docs here.
 */
public class TalonDriveSubsystem extends BitBucketSubsystem {
    private static final Logger log = LoggerFactory.getLogger(TalonDriveSubsystem.class);

    AHRS ahrs;

    final WPI_TalonFX[] leftMotors;
    final WPI_TalonFX[] rightMotors;

    final SendableChooser<DriveStyle> driveStyleChooser = new SendableChooser<>();
    DriveStyle currentDriveStyle;

    public TalonDriveSubsystem(Config config) {
        super(config);

        // initialize the motors from config
        leftMotors = initTalonsFromSettings(config, config.talonDriveConfig.leftMotors, WPI_TalonFX.class);
        rightMotors = initTalonsFromSettings(config, config.talonDriveConfig.rightMotors, WPI_TalonFX.class);

        /* drive uses velocity FPID */
        leaderTalonMotors.forEach(m -> m.selectProfileSlot(config.velocitySlotIndex, config.pidIndex));

        ahrs = new AHRS(SPI.Port.kMXP);
    }

    @Override
    public void initSmartDashboard() {
        super.initSmartDashboard();

        // init the smart dashboard with our drive style chooser
        driveStyleChooser.addOption("Manual", DriveStyle.Manual);
        driveStyleChooser.setDefaultOption("Velocity", DriveStyle.Velocity);
        SmartDashboard.putData(getName() + "/Drive Style", driveStyleChooser);

        currentDriveStyle = driveStyleChooser.getSelected();
    }

    @Override
    public void outputTelemetry() {
        /* Used to build string throughout loop */
        for (BaseTalon motor : allTalonMotors) {
            SmartDashboard.putNumber(getName() + "/Motor " + motor.getDeviceID() + "/out", motor.getMotorOutputPercent());
            SmartDashboard.putNumber(getName() + "/Motor " + motor.getDeviceID() + "/vel", motor.getSelectedSensorVelocity());
            SmartDashboard.putNumber(getName() + "/Motor " + motor.getDeviceID() + "/pos", motor.getSelectedSensorPosition());
            SmartDashboard.putNumber(getName() + "/Motor " + motor.getDeviceID() + "/err", motor.getClosedLoopError());
        }
        SmartDashboard.putNumber("Fused Heading", ahrs.getFusedHeading());
    }

    public DriveStyle getSelectedDriveStyle() {
        return driveStyleChooser.getSelected();
    }

    @Override
    public void periodic() {

    }

    public Command velocityDrive(final DoubleSupplier speed, final DoubleSupplier turn) {
        return new TalonVelocityDriveCommand(this, config.talonDriveConfig.maxVelocity, leftMotors[0], rightMotors[0], speed, turn);
    }

    public Command manualDrive(final DoubleSupplier speed, final DoubleSupplier turn) {
        return new ManualDriveCommand(this, leftMotors[0], rightMotors[0], speed, turn);

    }

    public void resetSensorPosition() {
        allTalonMotors.forEach((m) -> m.setSelectedSensorPosition(0));
    }

}
