package frc.robot.simulator.sim.utils;

import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.SimMotController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConversionUtils {
    private static final Logger log = LoggerFactory.getLogger(SimMotController.class);

    public static int ticksPerRevolution(int encoderCountsPerRevolution, RobotProto.MotorConfig.Encoder encoderType) {
        switch (encoderType) {

            // These are actually the same, they just have different sampling rates
            case QuadEncoder:
            case PulseWidthEncodedPosition:
            case NotSet: // If it hasn't been set yet, assume it's a regular encoder. Unset encoders will warn later if we try and get position data
                return encoderCountsPerRevolution * 4;
            case IntegratedSensor:
                // TODO: verify a TalonFX returns 2048 rather than 8192 ticks per revolution
                return encoderCountsPerRevolution;
            case Analog:
                return encoderCountsPerRevolution * 1;
            case None:
                // TODO: this is probably ok, but maybe not
                return 1;
            default:
//                log.warn("Tried to get ticksPerRevolution for an unsupported encoder type: " + encoderType.toString());
                return 1;
        }
    }

    public static int radiansToTicks(double rad, int ticksPerRevolution) {
        // 8192 ticks per 2PI radians
        return (int) (rad * (ticksPerRevolution / (2 * Math.PI)));
    }

    public static double ticksToRadians(int ticks, int ticksPerRevolution) {
        return ticks * 2 * Math.PI / ticksPerRevolution;
    }

    public static double radiansToRevolutions(double rad) {
        return rad / (2 * Math.PI);
    }

    public static double revolutionsToRadians(double revs) {
        return revs * (2 * Math.PI);
    }

    public static double radiansPerSecondToTicksPer100ms(double radsPerSecond, int ticksPerRevolution) {
        return perSecondToPer100ms(radiansToTicks(radsPerSecond, ticksPerRevolution));
    }

    public static double ticksPer100msToRadiansPerSecond(int ticksPer100ms, int ticksPerRevolution) {
        return per100msToPerSecond(ticksToRadians(ticksPer100ms, ticksPerRevolution));
    }

    public static double radiansPerSecondToRPM(double radsPerSecond) {
        return perSecondToPerMinute(radiansToRevolutions(radsPerSecond));
    }

    public static double rpmToRadiansPerSecond(double rpm) {
        return perMinuteToPerSecond(revolutionsToRadians(rpm));
    }

    /**
     * Convert a per second value to a per 100ms value
     * The CTRE calculations are always in ticks per 100ms or some such so 20,000 ticks per 100ms is actually
     * 200,000 ticks per second (ten 100ms in 1 second)
     *
     * @param val
     * @return
     */
    public static double perSecondToPer100ms(double val) {
        return val / 10;
    }

    public static double per100msToPerSecond(double val) {
        return val * 10;
    }

    /**
     * Convert a per minute (i.e. RPM) into a per second
     * So 60 RPMs = 1 revolution per second
     * @param val
     * @return
     */
    public static double perMinuteToPerSecond(double val) {
        return val / 60;
    }

    /**
     * Convert a per second value to per minute (i.e. RPM)
     * So 1 RPS would be 60 RPMs
     * @param val
     * @return
     */
    public static double perSecondToPerMinute(double val) {
        return val * 60;
    }

}
