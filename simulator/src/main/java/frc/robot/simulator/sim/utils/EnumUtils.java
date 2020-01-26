package frc.robot.simulator.sim.utils;

import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.SimException;

public class EnumUtils {

    public static RobotProto.MotorConfig.ControlMode controlModeFromInt(int mode) {
        switch (mode) {
            case 0:
                return RobotProto.MotorConfig.ControlMode.PercentOutput;
            case 1:
                return RobotProto.MotorConfig.ControlMode.Position;
            case 2:
                return RobotProto.MotorConfig.ControlMode.Velocity;
            case 3:
                return RobotProto.MotorConfig.ControlMode.Current;
            case 5:
                return RobotProto.MotorConfig.ControlMode.Follower;
            case 6:
                return RobotProto.MotorConfig.ControlMode.MotionProfile;
            case 7:
                return RobotProto.MotorConfig.ControlMode.MotionPosition;
            case 10:
                return RobotProto.MotorConfig.ControlMode.MotionProfileArc;
            case 15:
                return RobotProto.MotorConfig.ControlMode.Disabled;
            default:
                throw new SimException("Failed to determine ControlMode from " + mode);
        }
    }

    public static RobotProto.MotorConfig.ControlMode controlTypeFromInt(int mode) {
        switch (mode) {
            case 0:
                return RobotProto.MotorConfig.ControlMode.PercentOutput;
            case 1:
                return RobotProto.MotorConfig.ControlMode.Velocity;
            case 2:
                return RobotProto.MotorConfig.ControlMode.Voltage;
            case 3:
                return RobotProto.MotorConfig.ControlMode.Position;
            case 4:
                return RobotProto.MotorConfig.ControlMode.MotionPosition;
            case 5:
                return RobotProto.MotorConfig.ControlMode.Current;
            case 6:
                return RobotProto.MotorConfig.ControlMode.MotionVelocity;
            default:
                throw new SimException("Failed to determine ControlMode from " + mode);
        }
    }

}
