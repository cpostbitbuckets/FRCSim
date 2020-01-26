package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimSPI;

import java.nio.ByteBuffer;

public class SimSPIJNI extends JNIWrapper {
    public static void spiInitialize(int port) {
        SimSPI.spiInitialize(port);
    }

    public static int spiTransaction(int port, ByteBuffer dataToSend,
                                     ByteBuffer dataReceived, byte size) {
        return SimSPI.spiTransaction(port, dataToSend, dataReceived, size);
    }

    public static int spiTransactionB(int port, byte[] dataToSend,
                                      byte[] dataReceived, byte size) {
        return SimSPI.spiTransactionB(port, dataToSend, dataReceived, size);
    }

    public static int spiWrite(int port, ByteBuffer dataToSend, byte sendSize) {
        return SimSPI.spiWrite(port, dataToSend, sendSize);
    }

    public static int spiWriteB(int port, byte[] dataToSend, byte sendSize) {
        return SimSPI.spiWriteB(port, dataToSend, sendSize);
    }

    public static int spiRead(int port, boolean initiate, ByteBuffer dataReceived, byte size) {
        return SimSPI.spiRead(port, initiate, dataReceived, size);
    }

    public static int spiReadB(int port, boolean initiate, byte[] dataReceived, byte size) {
        return SimSPI.spiReadB(port, initiate, dataReceived, size);
    }

    public static void spiClose(int port) {
        SimSPI.spiClose(port);
    }

    public static void spiSetSpeed(int port, int speed) {
        SimSPI.spiSetSpeed(port, speed);
    }

    public static void spiSetOpts(int port, int msbFirst, int sampleOnTrailing,
                                  int clkIdleHigh) {
        SimSPI.spiSetOpts(port, msbFirst, sampleOnTrailing, clkIdleHigh);
    }

    public static void spiSetChipSelectActiveHigh(int port) {
        SimSPI.spiSetChipSelectActiveHigh(port);
    }

    public static void spiSetChipSelectActiveLow(int port) {
        SimSPI.spiSetChipSelectActiveLow(port);
    }

    public static void spiInitAuto(int port, int bufferSize) {
        SimSPI.spiInitAuto(port, bufferSize);
    }

    public static void spiFreeAuto(int port) {
        SimSPI.spiFreeAuto(port);
    }

    public static void spiStartAutoRate(int port, double period) {
        SimSPI.spiStartAutoRate(port, period);
    }

    public static void spiStartAutoTrigger(int port, int digitalSourceHandle,
                                           int analogTriggerType, boolean triggerRising,
                                           boolean triggerFalling) {
        SimSPI.spiStartAutoTrigger(port, digitalSourceHandle, analogTriggerType, triggerRising, triggerFalling);
    }

    public static void spiStopAuto(int port) {
        SimSPI.spiStopAuto(port);
    }

    public static void spiSetAutoTransmitData(int port, byte[] dataToSend, int zeroSize) {
        SimSPI.spiSetAutoTransmitData(port, dataToSend, zeroSize);
    }

    public static void spiForceAutoRead(int port) {
        SimSPI.spiForceAutoRead(port);
    }

    public static int spiReadAutoReceivedData(int port, ByteBuffer buffer, int numToRead,
                                              double timeout) {
        return SimSPI.spiReadAutoReceivedData(port, buffer, numToRead, timeout);
    }

    public static int spiReadAutoReceivedData(int port, int[] buffer, int numToRead,
                                              double timeout) {
        return SimSPI.spiReadAutoReceivedData(port, buffer, numToRead, timeout);
    }

    public static int spiGetAutoDroppedCount(int port) {
        return SimSPI.spiGetAutoDroppedCount(port);
    }

    public static void spiConfigureAutoStall(int port, int csToSclkTicks, int stallTicks, int pow2BytesPerRead) {
        SimSPI.spiConfigureAutoStall(port, csToSclkTicks, stallTicks, pow2BytesPerRead);
    }
}
