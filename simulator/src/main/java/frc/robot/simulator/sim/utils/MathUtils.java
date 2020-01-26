package frc.robot.simulator.sim.utils;

public class MathUtils {

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * Clamp values between -1 and 1
     * @param val
     * @return
     */
    public static double clamp(double val) {
        return clamp(val, -1, 1);
    }

}
