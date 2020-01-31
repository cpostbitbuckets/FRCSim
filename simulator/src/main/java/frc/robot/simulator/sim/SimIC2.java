package frc.robot.simulator.sim;

import frc.robot.simulator.sim.ic2.SimColorSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class SimIC2 {
    private static final Logger log = LoggerFactory.getLogger(SimIC2.class);

    private static final Map<Integer, SimColorSensor> devicesByPort = new HashMap<>();

    public static void i2CInitialize(int port) {
        devicesByPort.put(port, new SimColorSensor(port));
    }

    public static int i2CTransaction(int port, byte address, ByteBuffer dataToSend,
                                     byte sendSize, ByteBuffer dataReceived, byte receiveSize) {
        log.warn("i2CTransaction not implemented yet.");

        return 0;
    }

    public static int i2CTransactionB(int port, byte address, byte[] dataToSend,
                                      byte sendSize, byte[] dataReceived, byte receiveSize) {

        if (devicesByPort.containsKey(port)) {
            SimColorSensor colorSensor = devicesByPort.get(port);
            if (sendSize == 1) {
                byte request = dataToSend[0];
                if (request == SimColorSensor.Register.kPartID.bVal) {
                    dataReceived[0] = SimColorSensor.partId;
                } else if (request == SimColorSensor.Register.kDataGreen.bVal) {
                    dataReceived[0] = colorSensor.green;
                } else if (request == SimColorSensor.Register.kDataRed.bVal) {
                    dataReceived[0] = colorSensor.red;
                } else if (request == SimColorSensor.Register.kDataBlue.bVal) {
                    dataReceived[0] = colorSensor.blue;
                } else {
                    log.warn(String.format("IC2 device on port %s making unsupported request: %02X", port, dataToSend[0]));
                }
            }
        } else {
            log.warn("IC2 device on port " + port + " not found or not supported");
        }
        return 0;
    }

    public static int i2CWrite(int port, byte address, ByteBuffer dataToSend, byte sendSize) {
        log.warn("i2CWrite not implemented yet.");

        return 0;
    }

    public static int i2CWriteB(int port, byte address, byte[] dataToSend, byte sendSize) {
        log.warn("i2CWriteB not implemented yet.");

        return 0;
    }

    public static int i2CRead(int port, byte address, ByteBuffer dataReceived,
                              byte receiveSize) {
        log.warn("i2CRead not implemented yet.");

        return 0;
    }

    public static int i2CReadB(int port, byte address, byte[] dataReceived,
                               byte receiveSize) {
        log.warn("i2CReadB not implemented yet.");

        return 0;
    }

    public static void i2CClose(int port) {
        log.warn("i2CClose not implemented yet.");

    }

}
