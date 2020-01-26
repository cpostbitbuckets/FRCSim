package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.config.Config;
import frc.robot.subsystem.elevator.ElevatorSubsystem;

public class Robot extends TimedRobot {

    // config object used by each subsystem, but initialized by the Robot
    Config config;

    ElevatorSubsystem elevatorSubsystem;

    Joystick joystick = new Joystick(0);


    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        config = new Config();

        elevatorSubsystem = new ElevatorSubsystem(config);

    }

    @Override
    public void robotPeriodic() {
        // run the scheduler so all the robot commands are executed
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        if (joystick.getRawButton(1)) {
            elevatorSubsystem.moveToPosition(0);
        } else if (joystick.getRawButton(2)) {
            elevatorSubsystem.moveToPosition(10000);
        } else if (joystick.getRawButton(3)) {
            elevatorSubsystem.moveToPosition(20000);
        } else if (joystick.getRawButton(4)) {
            elevatorSubsystem.moveToPosition(40000);
        }
    }

    public static Robot win() {
        return new Robot();
    }
}
