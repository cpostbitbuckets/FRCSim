package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimIC2;

import java.nio.ByteBuffer;

public class SimI2CJNI extends JNIWrapper {
    public static void i2CInitialize(int port) {
        SimIC2.i2CInitialize(port);
    }

    public static int i2CTransaction(int port, byte address, ByteBuffer dataToSend,
                                     byte sendSize, ByteBuffer dataReceived, byte receiveSize) {
        return SimIC2.i2CTransaction(port, address, dataToSend, sendSize, dataReceived, receiveSize);
    }

    public static int i2CTransactionB(int port, byte address, byte[] dataToSend,
                                      byte sendSize, byte[] dataReceived, byte receiveSize) {
        return SimIC2.i2CTransactionB(port, address, dataToSend, sendSize, dataReceived, receiveSize);
    }

    public static int i2CWrite(int port, byte address, ByteBuffer dataToSend, byte sendSize) {
        return SimIC2.i2CWrite(port, address, dataToSend, sendSize);
    }

    public static int i2CWriteB(int port, byte address, byte[] dataToSend, byte sendSize) {
        return SimIC2.i2CWriteB(port, address, dataToSend, sendSize);
    }

    public static int i2CRead(int port, byte address, ByteBuffer dataReceived,
                              byte receiveSize) {
        return SimIC2.i2CRead(port, address, dataReceived, receiveSize);
    }

    public static int i2CReadB(int port, byte address, byte[] dataReceived,
                               byte receiveSize) {
        return SimIC2.i2CReadB(port, address, dataReceived, receiveSize);
    }

    public static void i2CClose(int port) {
        SimIC2.i2CClose(port);
    }
}
