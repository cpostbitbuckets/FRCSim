/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.mentor;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.config.Config;
import frc.robot.mentor.subsystem.BitBucketSubsystem;
import frc.robot.mentor.subsystem.arm.ArmSubsystem;
import frc.robot.mentor.subsystem.drive.DriveStyle;
import frc.robot.mentor.subsystem.drive.SparkDriveSubsystem;
import frc.robot.mentor.subsystem.drive.TalonDriveSubsystem;
import frc.robot.mentor.subsystem.shooter.ShooterSubsystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private static final Logger log = LoggerFactory.getLogger(Robot.class);

    // config object used by each subsystem, but initialized by the Robot
    private Config config;

    // controllers and buttons
    private Joystick driverJoystick;
    private Joystick operatorJoystick;
    JoystickButton moveToPositionZeroButton;
    JoystickButton moveToPositionForwardButton;
    JoystickButton moveToPositionBackwardButton;
    JoystickButton resetPositionSensorButton;
    JoystickButton resetDriveSensorButton;
    JoystickButton toggleSolenoidButton;
    JoystickButton spinUpShooterButton;

    // subsystems
    private TalonDriveSubsystem talonDriveSubsystem;
    private SparkDriveSubsystem sparkDriveSubsystem;
    private ArmSubsystem armSubsystem;
    private ShooterSubsystem shooterSubsystem;
    private List<BitBucketSubsystem> subsystems = new ArrayList<>();

    long currentTime = System.currentTimeMillis();


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        log.info("robotInit starting");
        config = new Config();

        // Make sure our preferences test works
        Preferences preferences = Preferences.getInstance();
        preferences.getString("testKey", "");
        preferences.putString("testKey", "testValue");


        // create some stuff to make sure the sim doesn't crash
        // TODO: this servo constantly calls getPWNPosition to update the live window
//        Servo servo  = new Servo(0);

        // init controllers
        driverJoystick = new Joystick(config.oi.driverId);
        operatorJoystick = new Joystick(config.oi.operatorId);
        moveToPositionZeroButton = new JoystickButton(operatorJoystick, config.oi.armZeroButton);
        moveToPositionForwardButton = new JoystickButton(operatorJoystick, config.oi.armForwardButton);
        moveToPositionBackwardButton = new JoystickButton(operatorJoystick, config.oi.armBackwardButton);
        resetPositionSensorButton = new JoystickButton(operatorJoystick, config.oi.armResetButton);
        resetDriveSensorButton = new JoystickButton(driverJoystick, config.oi.driveResetButton);
        toggleSolenoidButton = new JoystickButton(driverJoystick, config.oi.driveSolenoidButton);
        spinUpShooterButton = new JoystickButton(operatorJoystick, config.oi.shooterSpinUpButton);


        // init subsystems
        if (config.talonDriveConfig.enabled) {
            talonDriveSubsystem = new TalonDriveSubsystem(config);
            subsystems.add(talonDriveSubsystem);
        }
        if (config.sparkDriveConfig.enabled) {
            sparkDriveSubsystem = new SparkDriveSubsystem(config);
            subsystems.add(sparkDriveSubsystem);
        }
        if (config.armConfig.enabled) {
            armSubsystem = new ArmSubsystem(config);
            subsystems.add(armSubsystem);
        }
        if (config.shooterConfig.enabled) {
            shooterSubsystem = new ShooterSubsystem(config);
            subsystems.add(shooterSubsystem);
        }

        // initialize the global and subsystem dashboards
        initSmartDashboard();

        log.info("robotInit complete");
    }

    private void initSmartDashboard() {
        SmartDashboard.putBoolean("TelemetryEnabled", true);
        subsystems.forEach(s -> s.initSmartDashboard());
    }

    @Override
    public void disabledInit() {
        log.info("disabledInit starting");
        subsystems.forEach((s) -> s.idle());
        super.disabledInit();
        log.info("disabledInit complete");
    }

    @Override
    public void autonomousInit() {
        log.info("autonomousInit starting");
        super.autonomousInit();
        log.info("autonomousInit complete");
    }

    @Override
    public void testInit() {
        log.info("testInit starting");
        super.testInit();
        log.info("testInit complete");
    }

    @Override
    public void robotPeriodic() {
        // run the scheduler so all the robot commands are executed
        CommandScheduler.getInstance().run();
        if (SmartDashboard.getBoolean("TelemetryEnabled", true)) {
            outputTelemetry();

            // output telemetry of our subsystems
            subsystems.forEach(s -> s.outputTelemetry());
        }
    }

    /**
     * This function is called once when teleop mode is initiated
     */
    @Override
    public void teleopInit() {
        log.info("teleopInit starting");
        super.teleopInit();

        //
        // Positional Arm test
        //
        if (config.armConfig.enabled) {
            // handle joystick button presses
            moveToPositionZeroButton.whenPressed(armSubsystem::moveToPositionZero, armSubsystem);
            moveToPositionForwardButton.whenPressed(armSubsystem::moveToPositionForward, armSubsystem);
            moveToPositionBackwardButton.whenPressed(armSubsystem::moveToPositionBackward, armSubsystem);
            resetPositionSensorButton.whenPressed(armSubsystem::resetSensorPosition, armSubsystem);

            // toggle the solenoid as a test
            toggleSolenoidButton.whenPressed(armSubsystem::toggleSolenoids, armSubsystem);
        }

        //
        // Inverted Velocity TalonFX Shooter test
        //
        if (config.shooterConfig.enabled) {
            // when the button is held, set the velocity
            // and set it to 0 when released
            spinUpShooterButton
                    .whenHeld(new InstantCommand(shooterSubsystem::spinUp, shooterSubsystem))
                    .whenReleased(shooterSubsystem::stopSpinningUp, shooterSubsystem);

            // continuously check the feeder while the button is held
            spinUpShooterButton
                    .whileHeld(shooterSubsystem::feedIfReady, shooterSubsystem)
                    .whenReleased(shooterSubsystem::stopFeeding, shooterSubsystem);
        }

        //
        // TalonFX Drive test
        //
        if (config.talonDriveConfig.enabled) {
            // reset position
            resetDriveSensorButton.whenPressed(talonDriveSubsystem::resetSensorPosition, talonDriveSubsystem);

            // handle joystick axis
            talonDriveSubsystem.setDefaultCommand(talonDriveSubsystem.velocityDrive(
                    () -> -driverJoystick.getRawAxis(config.oi.driveForwardAxis),
                    () -> driverJoystick.getRawAxis(config.oi.driveTurnAxis)
            ));

            Trigger velocityDriveStyleTrigger = new Trigger(() -> talonDriveSubsystem.getSelectedDriveStyle() == DriveStyle.Velocity);
            Trigger manualDriveStyleTrigger = new Trigger(() -> talonDriveSubsystem.getSelectedDriveStyle() == DriveStyle.Manual);

            // Velocity drive is the default, use it when the trigger is active
            velocityDriveStyleTrigger.whenActive(talonDriveSubsystem.getDefaultCommand(), true);

            // if manual drive style is selected, switch the drive subsystem to use manual control
            manualDriveStyleTrigger.whenActive(
                    talonDriveSubsystem
                            .manualDrive(
                                    () -> -driverJoystick.getRawAxis(config.oi.driveForwardAxis),
                                    () -> driverJoystick.getRawAxis(config.oi.driveTurnAxis))
                    , true);

        }

        //
        // NEO Motor Drive test
        //
        if (config.sparkDriveConfig.enabled) {
            // spark drive is just for testing, velocity only
            sparkDriveSubsystem.setDefaultCommand(sparkDriveSubsystem
                    .drive(
                            () -> -driverJoystick.getRawAxis(config.oi.driveForwardAxis),
                            () -> driverJoystick.getRawAxis(config.oi.driveTurnAxis))
            );
            resetDriveSensorButton.whenPressed(sparkDriveSubsystem::resetSensorPosition, sparkDriveSubsystem);
        }
        log.info("teleopInit complete");
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        // EMPTY! AMAZING
    }

    /**
     * Output any global telemetry
     */
    private void outputTelemetry() {

        // output the deltaTime and our loops per second
        long newTime = System.currentTimeMillis();
        long deltaTime = newTime - currentTime;
        currentTime = newTime;
        double loopsPerSecond = (1.0 / deltaTime) * 1000;

        // set some global settings
        SmartDashboard.putNumber("deltaTime", deltaTime);
        SmartDashboard.putNumber("Loops per second", loopsPerSecond);
    }

    public static Robot win() {
        return new Robot();
    }
}
