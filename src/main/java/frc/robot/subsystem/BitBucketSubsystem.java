/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.config.Config;

/**
 * Abstract base class for all subsystems with some shared methods
 */
public abstract class BitBucketSubsystem extends Subsystem {
	/**
	 * Every subsystem needs configuration
	 */
	protected final Config config;

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

}