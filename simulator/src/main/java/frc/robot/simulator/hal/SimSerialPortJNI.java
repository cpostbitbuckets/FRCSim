package frc.robot.simulator.hal;

import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimSerialPort;

public class SimSerialPortJNI  extends JNIWrapper {
    public static int serialInitializePort(byte port) {
        return SimSerialPort.serialInitializePort(port);
    }

    public static int serialInitializePortDirect(byte port, String portName) {
        return SimSerialPort.serialInitializePortDirect(port, portName);
    }

    public static void serialSetBaudRate(int handle, int baud) {
        SimSerialPort.serialSetBaudRate(handle, baud);
    }

    public static void serialSetDataBits(int handle, byte bits) {
        SimSerialPort.serialSetDataBits(handle, bits);
    }

    public static void serialSetParity(int handle, byte parity) {
        SimSerialPort.serialSetParity(handle, parity);
    }

    public static void serialSetStopBits(int handle, byte stopBits) {
        SimSerialPort.serialSetStopBits(handle, stopBits);
    }

    public static void serialSetWriteMode(int handle, byte mode) {
        SimSerialPort.serialSetWriteMode(handle, mode);
    }

    public static void serialSetFlowControl(int handle, byte flow) {
        SimSerialPort.serialSetFlowControl(handle, flow);
    }

    public static void serialSetTimeout(int handle, double timeout) {
        SimSerialPort.serialSetTimeout(handle, timeout);
    }

    public static void serialEnableTermination(int handle, char terminator) {
        SimSerialPort.serialEnableTermination(handle, terminator);
    }

    public static void serialDisableTermination(int handle) {
        SimSerialPort.serialDisableTermination(handle);
    }

    public static void serialSetReadBufferSize(int handle, int size) {
        SimSerialPort.serialSetReadBufferSize(handle, size);
    }

    public static void serialSetWriteBufferSize(int handle, int size) {
        SimSerialPort.serialSetWriteBufferSize(handle, size);
    }

    public static int serialGetBytesReceived(int handle) {
        return SimSerialPort.serialGetBytesReceived(handle);
    }

    public static int serialRead(int handle, byte[] buffer, int count) {
        return SimSerialPort.serialRead(handle, buffer, count);
    }

    public static int serialWrite(int handle, byte[] buffer, int count) {
        return SimSerialPort.serialWrite(handle, buffer, count);
    }

    public static void serialFlush(int handle) {
        SimSerialPort.serialFlush(handle);
    }

    public static void serialClear(int handle) {
        SimSerialPort.serialClear(handle);
    }

    public static void serialClose(int handle) {
        SimSerialPort.serialClose(handle);
    }
}
