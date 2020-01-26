package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

public class SimSPI {
    private static final Logger log = LoggerFactory.getLogger(SimSPI.class);


    public static void spiInitialize(int port) {
        log.warn("spiInitialize not implemented yet.");

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

        //log.warn("spiWriteB not implemented yet.");

        return 0;
    }

    public static int spiRead(int port, boolean initiate, ByteBuffer dataReceived, byte size) {
        log.warn("spiRead not implemented yet.");

        return 0;
    }

    public static int spiReadB(int port, boolean initiate, byte[] dataReceived, byte size) {
        log.warn("spiReadB not implemented yet.");

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
        log.warn("spiSetOpts not implemented yet.");

    }

    public static void spiSetChipSelectActiveHigh(int port) {
        log.warn("spiSetChipSelectActiveHigh not implemented yet.");

    }

    public static void spiSetChipSelectActiveLow(int port) {
        log.warn("spiSetChipSelectActiveLow not implemented yet.");

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
