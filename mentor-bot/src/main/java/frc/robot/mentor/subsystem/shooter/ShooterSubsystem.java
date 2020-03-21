/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.mentor.subsystem.shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.config.Config;
import frc.robot.mentor.subsystem.BitBucketSubsystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShooterSubsystem extends BitBucketSubsystem {
    private static final Logger log = LoggerFactory.getLogger(ShooterSubsystem.class);

    final WPI_TalonFX[] shooter;
    final WPI_TalonSRX feeder;

    public ShooterSubsystem(Config config) {
        super(config);

        // initialize the motors from config
        shooter = initTalonsFromSettings(config, config.shooterConfig.shooterMotors, WPI_TalonFX.class);
        feeder = new WPI_TalonSRX(config.shooterConfig.feederMotor.id);

        // add the feeder motor to the motor list, but not the leaders or talon list
        allMotors.add(feeder);

        /* Shooter uses velocity */
        leaderTalonMotors.forEach(m -> m.selectProfileSlot(config.velocitySlotIndex, config.pidIndex));
    }

    @Override
    public void outputTelemetry() {
        /* Used to build string throughout loop */
        for (BaseTalon motor : allTalonMotors) {
            SmartDashboard.putNumber(getName() + "/Motor " + motor.getBaseID() + "/out", motor.getMotorOutputPercent());
            SmartDashboard.putNumber(getName() + "/Motor " + motor.getBaseID() + "/vel", motor.getSelectedSensorVelocity());
            SmartDashboard.putNumber(getName() + "/Motor " + motor.getBaseID() + "/pos", motor.getSelectedSensorPosition());
            SmartDashboard.putNumber(getName() + "/Motor " + motor.getBaseID() + "/err", motor.getClosedLoopError());
        }
    }

    /**
     * This sets the arm velocity
     */
    public void spinUp() {
        leaderTalonMotors.forEach(
                (m) -> m.set(ControlMode.Velocity, config.shooterConfig.spinUpSpeed)
        );
    }

    public void stopSpinningUp() {
        leaderTalonMotors.forEach(
                (m) -> m.set(ControlMode.PercentOutput, 0)
        );
    }

    public void feedIfReady() {
        // Spin up the feeder if the shooter is within 500 ticks of being up to speed.
        double error = Math.abs(shooter[0].getSelectedSensorVelocity() - config.shooterConfig.spinUpSpeed);
        if (error <= 500) {
            feeder.set(.5);
        } else {
            feeder.set(0);
        }
    }

    public void stopFeeding() {
        feeder.set(0);
    }
}
