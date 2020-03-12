
package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimSerialPort {
    private static final Logger log = LoggerFactory.getLogger(SimSerialPort.class);


    public static int serialInitializePort(byte port) {
        log.warn("serialInitializePort not implemented yet.");

        return 0;
    }

    public static int serialInitializePortDirect(byte port, String portName) {
        log.warn("serialInitializePortDirect not implemented yet.");

        return 0;
    }

    public static void serialSetBaudRate(int handle, int baud) {
        log.warn("serialSetBaudRate not implemented yet.");

    }

    public static void serialSetDataBits(int handle, byte bits) {
        log.warn("serialSetDataBits not implemented yet.");

    }

    public static void serialSetParity(int handle, byte parity) {
        log.warn("serialSetParity not implemented yet.");

    }

    public static void serialSetStopBits(int handle, byte stopBits) {
        log.warn("serialSetStopBits not implemented yet.");

    }

    public static void serialSetWriteMode(int handle, byte mode) {
        log.warn("serialSetWriteMode not implemented yet.");

    }

    public static void serialSetFlowControl(int handle, byte flow) {
        log.warn("serialSetFlowControl not implemented yet.");

    }

    public static void serialSetTimeout(int handle, double timeout) {
        log.warn("serialSetTimeout not implemented yet.");

    }

    public static void serialEnableTermination(int handle, char terminator) {
        log.warn("serialEnableTermination not implemented yet.");

    }

    public static void serialDisableTermination(int handle) {
        log.warn("serialDisableTermination not implemented yet.");

    }

    public static void serialSetReadBufferSize(int handle, int size) {
        log.warn("serialSetReadBufferSize not implemented yet.");

    }

    public static void serialSetWriteBufferSize(int handle, int size) {
        log.warn("serialSetWriteBufferSize not implemented yet.");

    }

    public static int serialGetBytesReceived(int handle) {
        log.warn("serialGetBytesReceived not implemented yet.");

        return 0;
    }

    public static int serialRead(int handle, byte[] buffer, int count) {
        log.warn("serialRead not implemented yet.");

        return 0;
    }

    public static int serialWrite(int handle, byte[] buffer, int count) {
        log.warn("serialWrite not implemented yet.");

        return 0;
    }

    public static void serialFlush(int handle) {
        log.warn("serialFlush not implemented yet.");

    }

    public static void serialClear(int handle) {
        log.warn("serialClear not implemented yet.");

    }

    public static void serialClose(int handle) {
        log.warn("serialClose not implemented yet.");

    }

}

