package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * Main entry point for the robot. This is called by the RoboRIO when the robot starts up
 */
public final class Main {
  private Main() {
  }

  /**
   * Our robot doesn't just 'new', it WINS
   */
  public static void main(String... args) {
    RobotBase.startRobot(Robot::win);
  }
}
