package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;

/**
 * Constants from sim/PortsInternal.h in allwpilib
 * <p>
 * constexpr int32_t kNumAccumulators = 2;
 * constexpr int32_t kNumAnalogTriggers = 8;
 * constexpr int32_t kNumAnalogInputs = 8;
 * constexpr int32_t kNumAnalogOutputs = 2;
 * constexpr int32_t kNumCounters = 8;
 * constexpr int32_t kNumDigitalHeaders = 10;
 * constexpr int32_t kNumPWMHeaders = 10;
 * constexpr int32_t kNumDigitalChannels = 26;
 * constexpr int32_t kNumPWMChannels = 20;
 * constexpr int32_t kNumDigitalPWMOutputs = 6;
 * constexpr int32_t kNumEncoders = 8;
 * constexpr int32_t kNumInterrupts = 8;
 * constexpr int32_t kNumRelayChannels = 8;
 * constexpr int32_t kNumRelayHeaders = kNumRelayChannels / 2;
 * constexpr int32_t kNumPCMModules = 63;
 * constexpr int32_t kNumSolenoidChannels = 8;
 * constexpr int32_t kNumPDPModules = 63;
 * constexpr int32_t kNumPDPChannels = 16;
 * constexpr int32_t kNumCanTalons = 63;
 */
public class SimPortsJNI extends JNIWrapper {
    public static int getNumAccumulators() {
        return 2;
    }

    public static int getNumAnalogTriggers() {
        return 8;
    }

    public static int getNumAnalogInputs() {
        return 8;
    }

    public static int getNumAnalogOutputs() {
        return 2;
    }

    public static int getNumCounters() {
        return 8;
    }

    public static int getNumDigitalHeaders() {
        return 10;
    }

    public static int getNumPWMHeaders() {
        return 10;
    }

    public static int getNumDigitalChannels() {
        return 26;
    }

    public static int getNumPWMChannels() {
        return 20;
    }

    public static int getNumDigitalPWMOutputs() {
        return 6;
    }

    public static int getNumEncoders() {
        return 8;
    }

    public static int getNumInterrupts() {
        return 8;
    }

    public static int getNumRelayChannels() {
        return 8;
    }

    public static int getNumRelayHeaders() {
        return getNumRelayChannels() / 2;
    }

    public static int getNumPCMModules() {
        return 63;
    }

    public static int getNumSolenoidChannels() {
        return 8;
    }

    public static int getNumPDPModules() {
        return 63;
    }

    public static int getNumPDPChannels() {
        return 16;
    }
}
