package frc.robot.simulator.sim;

import com.kauailabs.navx.AHRSProtocol;
import com.kauailabs.navx.IMURegisters;
import edu.wpi.first.wpilibj.SPI;
import frc.robot.simulator.sim.ic2.SimNavX;
import frc.robot.simulator.sim.ic2.SimSPIDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static com.kauailabs.navx.AHRSProtocol.encodeBinaryUint32;
import static com.kauailabs.navx.AHRSProtocol.encodeProtocolSignedHundredthsFloat;

public class SimSPI {
    private static final Logger log = LoggerFactory.getLogger(SimSPI.class);

    private static final Map<Integer, SimSPIDevice> devicesByPort = new HashMap<>();

    public static SimNavX getNavX(int port) {
        SimSPIDevice device = devicesByPort.get(port);
        if (device != null && device instanceof SimNavX) {
            return (SimNavX) device;
        }
        return null;
    }

    /**
     * This is called when creating a navx AHRS device
     * @param port
     */
    public static void spiInitialize(int port) {
        if (port == SPI.Port.kMXP.value) {
            devicesByPort.put(port, new SimNavX(port));
        } else {
            log.warn("spiInitialize for port " + port + " not implemented yet.");
        }
    }

    public static int spiTransaction(int port, ByteBuffer dataToSend,
                                     ByteBuffer dataReceived, byte size) {
        log.warn("spiTransaction not implemented yet.");

        return 0;
    }

    public static int spiTransactionB(int port, byte[] dataToSend,
                                      byte[] dataReceived, byte size) {
        log.warn("spiTransactionB not implemented yet.");

        return 0;
    }

    public static int spiWrite(int port, ByteBuffer dataToSend, byte sendSize) {
        log.warn("spiWrite not implemented yet.");

        return 0;
    }

    public static int spiWriteB(int port, byte[] dataToSend, byte sendSize) {
        SimSPIDevice device = devicesByPort.get(port);
        if (device != null) {
            boolean failed = false;
            SimNavX simNavX = (SimNavX)device;
            byte command = (byte) (dataToSend[0] &~ (byte)0x80);
            switch (command) {
                case IMURegisters.NAVX_REG_UPDATE_RATE_HZ:
                   simNavX.updateRateHz = dataToSend[1];
                   break;
                case IMURegisters.NAVX_REG_WHOAMI:
                    // this is a request for a read, so say "sure, we can let you read this"
                    return sendSize;
                default:
                    log.warn("spiWriteB not for command " + command + " for port: " + port + " not implemented yet");
                    break;
            }
            if (failed) {
                return 0;
            } else {
                return sendSize;
            }
        } else {
            log.warn("spiWriteB not implemented for port: " + port);
        }
        return 0;
    }

    public static int spiRead(int port, boolean initiate, ByteBuffer dataReceived, byte size) {
        log.warn("spiRead not implemented yet.");

        return 0;
    }

    public static int spiReadB(int port, boolean initiate, byte[] dataReceived, byte size) {
        // grab the device by port, we only really have Navx devices right now
        SimSPIDevice device = devicesByPort.get(port);
        if (device != null) {
            SimNavX simNavX = (SimNavX)device;
            byte command = (byte) (dataReceived[0] &~ (byte)0x80);
            switch (command) {
                case IMURegisters.NAVX_REG_WHOAMI:
                    if (size == 19) {
                        // this is a capabilities read
                        // TODO: fill this out?
                        byte crc = AHRSProtocol.getCRC(dataReceived, dataReceived.length - 1);
                        dataReceived[dataReceived.length - 1] = crc;
                        return size;
                    } else {
                        //
                        // it wants a 4byte timestamp, but we only have 8byte longs...
                        encodeBinaryUint32((int) System.currentTimeMillis(), dataReceived, IMURegisters.NAVX_REG_TIMESTAMP_L_L - port);

                        // write the heading to heading and yaw, we never have pitch in our 2d world
                        encodeProtocolSignedHundredthsFloat(simNavX.heading, dataReceived, IMURegisters.NAVX_REG_FUSED_HEADING_L - port);
                        encodeProtocolSignedHundredthsFloat(simNavX.heading, dataReceived, IMURegisters.NAVX_REG_HEADING_L - port);
                        encodeProtocolSignedHundredthsFloat(simNavX.heading, dataReceived, IMURegisters.NAVX_REG_YAW_L - port);
                        encodeProtocolSignedHundredthsFloat(simNavX.heading, dataReceived, IMURegisters.NAVX_REG_YAW_OFFSET_L - port);

                        // this is a read, tell the caller we can return a big buffer of info
                        byte crc = AHRSProtocol.getCRC(dataReceived, dataReceived.length - 1);
                        dataReceived[dataReceived.length - 1] = crc;
                        return size;
                    }
                default:
                    log.warn("spiReadB not for command " + command + " for port: " + port + " not implemented yet");
                    break;
            }
        }
        log.warn("spiReadB not implemented for port: " + port);
        return 0;
    }

    public static void spiClose(int port) {
        log.warn("spiClose not implemented yet.");

    }

    public static void spiSetSpeed(int port, int speed) {
        log.warn("spiSetSpeed not implemented yet.");

    }

    public static void spiSetOpts(int port, int msbFirst, int sampleOnTrailing,
                                  int clkIdleHigh) {
        SimSPIDevice device = devicesByPort.get(port);
        if (device != null) {
            SimNavX simNavX = (SimNavX)device;
            simNavX.setOps(msbFirst == 1, sampleOnTrailing == 1, clkIdleHigh == 1);
        } else {
            log.warn("spiSetOpts not implemented for port: " + port);
        }

    }

    public static void spiSetChipSelectActiveHigh(int port) {
        log.warn("spiSetChipSelectActiveHigh not implemented yet.");

    }

    public static void spiSetChipSelectActiveLow(int port) {
        SimSPIDevice device = devicesByPort.get(port);
        if (device != null) {
            SimNavX simNavX = (SimNavX)device;
            simNavX.setChipSelectActiveLow(true);
        } else {
            log.warn("spiSetChipSelectActiveLow not implemented for port: " + port);
        }

    }

    public static void spiInitAuto(int port, int bufferSize) {
        log.warn("spiInitAuto not implemented yet.");

    }

    public static void spiFreeAuto(int port) {
        log.warn("spiFreeAuto not implemented yet.");

    }

    public static void spiStartAutoRate(int port, double period) {
        log.warn("spiStartAutoRate not implemented yet.");

    }

    public static void spiStartAutoTrigger(int port, int digitalSourceHandle,
                                           int analogTriggerType, boolean triggerRising,
                                           boolean triggerFalling) {
        log.warn("spiStartAutoTrigger not implemented yet.");

    }

    public static void spiStopAuto(int port) {
        log.warn("spiStopAuto not implemented yet.");

    }

    public static void spiSetAutoTransmitData(int port, byte[] dataToSend, int zeroSize) {
        log.warn("spiSetAutoTransmitData not implemented yet.");

    }

    public static void spiForceAutoRead(int port) {
        log.warn("spiForceAutoRead not implemented yet.");

    }

    public static int spiReadAutoReceivedData(int port, ByteBuffer buffer, int numToRead,
                                              double timeout) {
        log.warn("spiReadAutoReceivedData not implemented yet.");

        return 0;
    }

    public static int spiReadAutoReceivedData(int port, int[] buffer, int numToRead,
                                              double timeout) {
        log.warn("spiReadAutoReceivedData not implemented yet.");

        return 0;
    }

    public static int spiGetAutoDroppedCount(int port) {
        log.warn("spiGetAutoDroppedCount not implemented yet.");

        return 0;
    }

    public static void spiConfigureAutoStall(int port, int csToSclkTicks, int stallTicks, int pow2BytesPerRead) {
        log.warn("spiConfigureAutoStall not implemented yet.");
    }
}
