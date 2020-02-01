/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.mentor.subsystem;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.config.Config;
import frc.robot.config.settings.MotorSettings;
import frc.robot.config.settings.SensorType;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all subsystems with some shared methods
 */
public abstract class BitBucketSubsystem extends SubsystemBase implements Subsystem {
	/**
	 * Every subsystem needs configuration
	 */
	protected final Config config;

	protected final List<BaseTalon> allTalonMotors = new ArrayList<>();
	protected final List<BaseTalon> leaderTalonMotors = new ArrayList<>();

	protected final List<CANSparkMax> allSparkMotors = new ArrayList<>();
	protected final List<CANSparkMax> leaderSparkMotors = new ArrayList<>();

	protected final List<SpeedController> allMotors = new ArrayList<>();

	public BitBucketSubsystem(Config config) {
		super();
		// name this subsystem
		setName(getClass().getSimpleName());

		this.config = config;
	}

	/**
	 * Any periodic output to the SmartDashboard should go here
	 */
	public void outputTelemetry() {}

	/**
	 * Any settings we want configured in the dashboard
	 */
	public void initSmartDashboard() {}

	/**
	 * Initialize an array of motors from an array of MotorSettings objects
	 * This assumes the first motor in the array is the leader, the rest are the followers
	 * @param config
	 * @param motorsSettings
	 * @return
	 */
	protected <T extends BaseTalon> T[] initTalonsFromSettings(Config config, MotorSettings[] motorsSettings, Class<T> clz) {
		T[] motors = (T[]) Array.newInstance(clz, motorsSettings.length);
		int motorNum = 0;
		for (MotorSettings settings : motorsSettings) {
			// create the motor and add it to our array of motors
			T motor = null;
			try {
				motor = clz.getDeclaredConstructor(Integer.TYPE).newInstance(settings.id);
			} catch (Exception e) {
				throw new RuntimeException("Can't create talong motor of type: " + clz.getSimpleName(), e);
			}

			// initialize the motor config to defaults
			motor.configFactoryDefault();

			// Invert Motor to have green LEDs when driving Talon Forward / Requesting Postiive Output
			motor.setInverted(settings.inverted);

			if (settings.encoder != SensorType.None) {
				switch (settings.encoder) {

					case Quadrature:
						/* Configure Sensor Source for velocity PID */
						motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
								config.velocitySlotIndex,
								config.ctreTimeout);

						/* Configure Sensor Source for position PID */
						motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
								config.positionSlotIndex,
								config.ctreTimeout);
						break;
					case Relative:
						/* Configure Sensor Source for velocity PID */
						motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
								config.velocitySlotIndex,
								config.ctreTimeout);

						/* Configure Sensor Source for position PID */
						motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
								config.positionSlotIndex,
								config.ctreTimeout);
						break;
					case Absolute:
						/* Configure Sensor Source for velocity PID */
						motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,
								config.velocitySlotIndex,
								config.ctreTimeout);

						/* Configure Sensor Source for position PID */
						motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,
								config.positionSlotIndex,
								config.ctreTimeout);
						break;
					case Integrated:
						/* Configure Sensor Source for velocity PID */
						motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor,
								config.velocitySlotIndex,
								config.ctreTimeout);

						/* Configure Sensor Source for position PID */
						motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor,
								config.positionSlotIndex,
								config.ctreTimeout);
						break;
				}
			}

			if (motorNum == 0) {
				// first motor is always the leader
				leaderTalonMotors.add(motor);


				/**
				 * Configure Talon SRX Sensor direction accordingly
				 * Phase sensor to have positive increment when driving Talon Forward (Green LED)
				 */
				motor.setSensorPhase(settings.sensorPhase);

				/* Set relevant frame periods to be at least as fast as periodic rate */
				motor.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10);
				motor.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10);

				/* Set the peak and nominal outputs */
				motor.configNominalOutputForward(0);
				motor.configNominalOutputReverse(0);
				motor.configPeakOutputForward(1);
				motor.configPeakOutputReverse(-1);

				/* Set acceleration and vcruise velocity - see documentation */
				motor.configMotionCruiseVelocity(settings.motionMagicVelocity);
				motor.configMotionAcceleration(settings.motionMagicAcceleration);

				/* Zero the sensor */
				motor.setSelectedSensorPosition(0);

				// configure velocity PID constants
				motor.config_kF(config.positionSlotIndex, settings.positionFPID.f);
				motor.config_kP(config.positionSlotIndex, settings.positionFPID.p);
				motor.config_kI(config.positionSlotIndex, settings.positionFPID.i);
				motor.config_kD(config.positionSlotIndex, settings.positionFPID.d);

				// configure velocity PID constants
				motor.config_kF(config.velocitySlotIndex, settings.velocityFPID.f);
				motor.config_kP(config.velocitySlotIndex, settings.velocityFPID.p);
				motor.config_kI(config.velocitySlotIndex, settings.velocityFPID.i);
				motor.config_kD(config.velocitySlotIndex, settings.velocityFPID.d);

			} else {
				// configure follower
				if (settings.follower) {
					motor.follow(motors[0]);
				}
			}
			motors[motorNum++] = motor;
			allTalonMotors.add(motor);
			allMotors.add((SpeedController) motor);
		}

		return motors;
	}

	protected CANSparkMax[] initSparksFromSettings(Config config, MotorSettings[] motorsSettings) {
		CANSparkMax[] motors = new CANSparkMax[motorsSettings.length];
		int motorNum = 0;
		for (MotorSettings settings : motorsSettings) {
			// create the motor and add it to our array of motors
			var motor = new CANSparkMax(settings.id, CANSparkMaxLowLevel.MotorType.kBrushless);

			// set inverted flag
			motor.setInverted(settings.inverted);

			if (motorNum == 0) {
				// first motor is always the leader
				leaderSparkMotors.add(motor);

				/* Configure Sensor Source for velocity PID */
				CANEncoder encoder = motor.getEncoder(EncoderType.kQuadrature, 8192);
				/* Zero the sensor */
				encoder.setPosition(0);
				// brushless motors can't be inverted
				// encoder.setInverted(settings.sensorPhase);

				/* Set acceleration and vcruise velocity - see documentation */
				CANPIDController pidController = motor.getPIDController();
				pidController.setSmartMotionMaxVelocity(settings.motionMagicVelocity, config.velocitySlotIndex);
				pidController.setSmartMotionMaxAccel(settings.motionMagicAcceleration, config.velocitySlotIndex);
				pidController.setSmartMotionMaxVelocity(settings.motionMagicVelocity, config.positionSlotIndex);
				pidController.setSmartMotionMaxAccel(settings.motionMagicAcceleration, config.positionSlotIndex);

				// configure position PID constants
				pidController.setFF(settings.positionFPID.f, config.positionSlotIndex);
				pidController.setP(settings.positionFPID.p, config.positionSlotIndex);
				pidController.setI(settings.positionFPID.i, config.positionSlotIndex);
				pidController.setD(settings.positionFPID.d, config.positionSlotIndex);
				pidController.setIZone(settings.positionFPID.iZone, config.positionSlotIndex);

				// configure velocity PID constants
				pidController.setFF(settings.velocityFPID.f, config.velocitySlotIndex);
				pidController.setP(settings.velocityFPID.p, config.velocitySlotIndex);
				pidController.setI(settings.velocityFPID.i, config.velocitySlotIndex);
				pidController.setD(settings.velocityFPID.d, config.velocitySlotIndex);
				pidController.setIZone(settings.velocityFPID.iZone, config.velocitySlotIndex);

			} else {
				// configure follower
				if (settings.follower) {
					motor.follow(motors[0]);
				}
			}
			motors[motorNum++] = motor;
			allSparkMotors.add(motor);
			allMotors.add(motor);
		}

		return motors;
	}

	/**
	 * Force all motors in this subsystem to idle
	 */
	public void idle() {
		allMotors.forEach(m -> m.set(0));
	}
}
