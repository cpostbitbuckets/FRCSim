/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.mentor.subsystem.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.config.Config;
import frc.robot.mentor.subsystem.BitBucketSubsystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Add your docs here.
 */
public class ArmSubsystem extends BitBucketSubsystem {
    private static final Logger log = LoggerFactory.getLogger(ArmSubsystem.class);

    final WPI_TalonSRX[] armMotors;

    public ArmSubsystem(Config config) {
        super(config);

        // initialize the motors from config
        armMotors = initTalonsFromSettings(config, config.armConfig.armMotors, WPI_TalonSRX.class);

        /* Arm control uses motion magic FPID */
        leaderTalonMotors.forEach(m -> m.selectProfileSlot(config.positionSlotIndex, config.pidIndex));
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
     * This moves the arm to a position, in ticks
     * @param position
     */
    private void moveToPosition(int position) {
        leaderTalonMotors.forEach(
                (m) -> m.set(ControlMode.MotionMagic, position)
        );
    }


    //
    // Actions this subsystem can execute. These are used by commands
    //

    public void resetSensorPosition() {
        allTalonMotors.forEach(m -> m.setSelectedSensorPosition(0));
    }

    public void moveToPositionZero() {
        moveToPosition(0);
    }

    public void moveToPositionForward() {
        moveToPosition(81920);
    }

    public void moveToPositionBackward() {
        moveToPosition(-81920);
    }


}
