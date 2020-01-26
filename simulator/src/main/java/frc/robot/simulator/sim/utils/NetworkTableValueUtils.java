package frc.robot.simulator.sim.utils;

import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.networktables.NetworkTableValue;
import frc.robot.simulator.sim.SimException;
import frc.robot.simulator.sim.SimNetworkTables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

public class NetworkTableValueUtils {
    private static final Logger log = LoggerFactory.getLogger(SimNetworkTables.class);

    static Constructor<NetworkTableValue> constructor;

    static {
        try {
            constructor = NetworkTableValue.class.getDeclaredConstructor(NetworkTableType.class, Object.class, long.class );
            constructor.setAccessible(true);
        } catch (Exception e) {
            log.error("Failed to make NetworkTableValue constructor public.", e);
            throw new SimException("Failed to make NetworkTableValue constructor public.", e);
        }
    }

    /**
     * Helper method to create NetworkTableValue instances, since the stupid constructor is private and the class if final. ugh
     * @param type
     * @param time
     * @param value
     * @return
     */
    public static NetworkTableValue create(NetworkTableType type, long time, Object value) {
        try {
            return constructor.newInstance(type, value, time);
        } catch (Exception e) {
            log.error("Failed to create NetworkTableValue.", e);
            throw new SimException("Failed to create NetworkTableInstance", e);
        }
    }
}
