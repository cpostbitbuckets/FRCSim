
package frc.robot.simulator.sim;

import edu.wpi.first.networktables.*;
import frc.robot.simulator.sim.utils.NetworkTableValueUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class SimNetworkTables {
    private static final Logger log = LoggerFactory.getLogger(SimNetworkTables.class);

    private static int defaultInstanceHandle = 1;
    private static int nextEntryIndex = 1;
    private static Map<String, Integer> keyToEntryIndex = new HashMap<>();
    private static Map<Integer, NetworkTableValue> entryIdToObject = new HashMap<>();

    public static int getDefaultInstance() {
        return defaultInstanceHandle;
    }

    public static int createInstance() {
        log.warn("createInstance not implemented yet.");

        return 0;
    }

    public static void destroyInstance(int inst) {
        log.warn("destroyInstance not implemented yet.");

    }

    public static int getInstanceFromHandle(int handle) {
        log.warn("getInstanceFromHandle not implemented yet.");

        return 0;
    }

    public static int getEntry(int inst, String key) {
//        log.warn("getEntry not implemented yet.");
        // get the entry index for this key, or return our nextEntryIndex (and increment by one so the  next call to this gets a new entry index
        int entryIndex = keyToEntryIndex.getOrDefault(key, nextEntryIndex++);
        keyToEntryIndex.put(key, entryIndex);
        return entryIndex;
    }

    public static int[] getEntries(int inst, String prefix, int types) {
        log.warn("getEntries not implemented yet.");

        return new int[] {};
    }

    public static String getEntryName(int entry) {
        log.warn("getEntryName not implemented yet.");

        return null;
    }

    public static long getEntryLastChange(int entry) {
        log.warn("getEntryLastChange not implemented yet.");

        return 0;
    }

    public static int getType(int entry) {
        log.warn("getType not implemented yet.");

        return 0;
    }

    public static boolean setBoolean(int entry, long time, boolean value, boolean force) {
//        log.warn("setBoolean not implemented yet.");
        entryIdToObject.put(entry, NetworkTableValueUtils.create(NetworkTableType.kBoolean, time, value));

        return true;
    }

    public static boolean setDouble(int entry, long time, double value, boolean force) {
//        log.warn("setDouble not implemented yet.");

        entryIdToObject.put(entry, NetworkTableValueUtils.create(NetworkTableType.kDouble, time, value));
        return true;
    }

    public static boolean setString(int entry, long time, String value, boolean force) {
//        log.warn("setString not implemented yet.");

        entryIdToObject.put(entry, NetworkTableValueUtils.create(NetworkTableType.kString, time, value));
        return true;
    }

    public static boolean setRaw(int entry, long time, byte[] value, boolean force) {
//        log.warn("setRaw not implemented yet.");

        entryIdToObject.put(entry, NetworkTableValueUtils.create(NetworkTableType.kRaw, time, value));
        return true;
    }

    public static boolean setRaw(int entry, long time, ByteBuffer value, int len, boolean force) {
//        log.warn("setRaw not implemented yet.");

        entryIdToObject.put(entry, NetworkTableValueUtils.create(NetworkTableType.kRaw, time, value));
        return true;
    }

    public static boolean setBooleanArray(int entry, long time, boolean[] value, boolean force) {
//        log.warn("setBooleanArray not implemented yet.");

        entryIdToObject.put(entry, NetworkTableValueUtils.create(NetworkTableType.kBooleanArray, time, value));
        return true;
    }

    public static boolean setDoubleArray(int entry, long time, double[] value, boolean force) {
//        log.warn("setDoubleArray not implemented yet.");

        entryIdToObject.put(entry, NetworkTableValueUtils.create(NetworkTableType.kDoubleArray, time, value));
        return true;
    }

    public static boolean setStringArray(int entry, long time, String[] value, boolean force) {
//        log.warn("setStringArray not implemented yet.");

        entryIdToObject.put(entry, NetworkTableValueUtils.create(NetworkTableType.kStringArray, time, value));
        return true;
    }

    public static NetworkTableValue getValue(int entry) {
        return entryIdToObject.get(entry);
    }

    public static boolean getBoolean(int entry, boolean defaultValue) {
        log.warn("getBoolean not implemented yet.");

        return false;
    }

    public static double getDouble(int entry, double defaultValue) {
        log.warn("getDouble not implemented yet.");

        return 0;
    }

    public static String getString(int entry, String defaultValue) {
        log.warn("getString not implemented yet.");

        return null;
    }

    public static byte[] getRaw(int entry, byte[] defaultValue) {
        log.warn("getRaw not implemented yet.");

        return null;
    }

    public static boolean[] getBooleanArray(int entry, boolean[] defaultValue) {
        log.warn("getBooleanArray not implemented yet.");

        return null;
    }

    public static double[] getDoubleArray(int entry, double[] defaultValue) {
//        log.warn("getDoubleArray not implemented yet.");
        if (entryIdToObject.containsKey(entry)) {
            return entryIdToObject.get(entry).getDoubleArray();
        }
        return defaultValue;
    }

    public static String[] getStringArray(int entry, String[] defaultValue) {
        log.warn("getStringArray not implemented yet.");

        return null;
    }

    public static boolean setDefaultBoolean(int entry, long time, boolean defaultValue) {
        log.warn("setDefaultBoolean not implemented yet.");

        return false;
    }

    public static boolean setDefaultDouble(int entry, long time, double defaultValue) {
        log.warn("setDefaultDouble not implemented yet.");

        return false;
    }

    public static boolean setDefaultString(int entry, long time, String defaultValue) {
        log.warn("setDefaultString not implemented yet.");

        return false;
    }

    public static boolean setDefaultRaw(int entry, long time, byte[] defaultValue) {
        log.warn("setDefaultRaw not implemented yet.");

        return false;
    }

    public static boolean setDefaultBooleanArray(int entry, long time, boolean[] defaultValue) {
        log.warn("setDefaultBooleanArray not implemented yet.");

        return false;
    }

    public static boolean setDefaultDoubleArray(int entry, long time, double[] defaultValue) {
        log.warn("setDefaultDoubleArray not implemented yet.");

        return false;
    }

    public static boolean setDefaultStringArray(int entry, long time, String[] defaultValue) {
        log.warn("setDefaultStringArray not implemented yet.");

        return false;
    }

    public static void setEntryFlags(int entry, int flags) {
        log.warn("setEntryFlags not implemented yet.");

    }

    public static int getEntryFlags(int entry) {
        log.warn("getEntryFlags not implemented yet.");

        return 0;
    }

    public static void deleteEntry(int entry) {
        log.warn("deleteEntry not implemented yet.");

    }

    public static void deleteAllEntries(int inst) {
        log.warn("deleteAllEntries not implemented yet.");

    }

    public static EntryInfo getEntryInfoHandle(NetworkTableInstance inst, int entry) {
        log.warn("getEntryInfoHandle not implemented yet.");

        return null;
    }

    public static EntryInfo[] getEntryInfo(NetworkTableInstance instObject, int inst, String prefix, int types) {
        log.warn("getEntryInfo not implemented yet.");

        return null;
    }

    public static int createEntryListenerPoller(int inst) {
        log.warn("createEntryListenerPoller not implemented yet.");

        return 0;
    }

    public static void destroyEntryListenerPoller(int poller) {
        log.warn("destroyEntryListenerPoller not implemented yet.");

    }

    public static int addPolledEntryListener(int poller, String prefix, int flags) {
        log.warn("addPolledEntryListener not implemented yet.");

        return 0;
    }

    public static int addPolledEntryListener(int poller, int entry, int flags) {
        log.warn("addPolledEntryListener not implemented yet.");

        return 0;
    }

    public static void removeEntryListener(int entryListener) {
        log.warn("removeEntryListener not implemented yet.");

    }

    public static boolean waitForEntryListenerQueue(int inst, double timeout) {
        log.warn("waitForEntryListenerQueue not implemented yet.");

        return false;
    }

    public static int createConnectionListenerPoller(int inst) {
        log.warn("createConnectionListenerPoller not implemented yet.");

        return 0;
    }

    public static void destroyConnectionListenerPoller(int poller) {
        log.warn("destroyConnectionListenerPoller not implemented yet.");

    }

    public static int addPolledConnectionListener(int poller, boolean immediateNotify) {
        log.warn("addPolledConnectionListener not implemented yet.");

        return 0;
    }

    public static void removeConnectionListener(int connListener) {
        log.warn("removeConnectionListener not implemented yet.");

    }

    public static boolean waitForConnectionListenerQueue(int inst, double timeout) {
        log.warn("waitForConnectionListenerQueue not implemented yet.");

        return false;
    }

    public static int createRpcCallPoller(int inst) {
        log.warn("createRpcCallPoller not implemented yet.");

        return 0;
    }

    public static void destroyRpcCallPoller(int poller) {
        log.warn("destroyRpcCallPoller not implemented yet.");

    }

    public static void createPolledRpc(int entry, byte[] def, int poller) {
        log.warn("createPolledRpc not implemented yet.");

    }

    public static boolean waitForRpcCallQueue(int inst, double timeout) {
        log.warn("waitForRpcCallQueue not implemented yet.");

        return false;
    }

    public static boolean postRpcResponse(int entry, int call, byte[] result) {
        log.warn("postRpcResponse not implemented yet.");

        return false;
    }

    public static int callRpc(int entry, byte[] params) {
        log.warn("callRpc not implemented yet.");

        return 0;
    }

    public static byte[] getRpcResult(int entry, int call) {
        log.warn("getRpcResult not implemented yet.");

        return null;
    }

    public static byte[] getRpcResult(int entry, int call, double timeout) {
        log.warn("getRpcResult not implemented yet.");

        return null;
    }

    public static void cancelRpcResult(int entry, int call) {
        log.warn("cancelRpcResult not implemented yet.");

    }

    public static byte[] getRpc(int entry, byte[] defaultValue) {
        log.warn("getRpc not implemented yet.");

        return null;
    }

    public static void setNetworkIdentity(int inst, String name) {
        log.debug("setNetworkIdentity to " + name);
        // ignore
    }

    public static int getNetworkMode(int inst) {
        log.warn("getNetworkMode not implemented yet.");

        return 0;
    }

    public static void startServer(int inst, String persistFilename, String listenAddress, int port) {
//        log.warn("startServer not implemented yet.");
        // ignore. We don't actually run a server
    }

    public static void stopServer(int inst) {
        log.warn("stopServer not implemented yet.");

    }

    public static void startClient(int inst) {
        log.warn("startClient not implemented yet.");

    }

    public static void startClient(int inst, String serverName, int port) {
        log.warn("startClient not implemented yet.");

    }

    public static void startClient(int inst, String[] serverNames, int[] ports) {
        log.warn("startClient not implemented yet.");

    }

    public static void startClientTeam(int inst, int team, int port) {
        log.warn("startClientTeam not implemented yet.");

    }

    public static void stopClient(int inst) {
        log.warn("stopClient not implemented yet.");

    }

    public static void setServer(int inst, String serverName, int port) {
        log.warn("setServer not implemented yet.");

    }

    public static void setServer(int inst, String[] serverNames, int[] ports) {
        log.warn("setServer not implemented yet.");

    }

    public static void setServerTeam(int inst, int team, int port) {
        log.warn("setServerTeam not implemented yet.");

    }

    public static void startDSClient(int inst, int port) {
        log.warn("startDSClient not implemented yet.");

    }

    public static void stopDSClient(int inst) {
        log.warn("stopDSClient not implemented yet.");

    }

    public static void setUpdateRate(int inst, double interval) {
        log.warn("setUpdateRate not implemented yet.");

    }

    public static void flush(int inst) {
        log.warn("flush not implemented yet.");

    }

    public static ConnectionInfo[] getConnections(int inst) {
        log.warn("getConnections not implemented yet.");

        return null;
    }

    public static boolean isConnected(int inst) {
        log.warn("isConnected not implemented yet.");

        return false;
    }

    public static int createLoggerPoller(int inst) {
        log.warn("createLoggerPoller not implemented yet.");

        return 0;
    }

    public static void destroyLoggerPoller(int poller) {
        log.warn("destroyLoggerPoller not implemented yet.");

    }

    public static int addPolledLogger(int poller, int minLevel, int maxLevel) {
        log.warn("addPolledLogger not implemented yet.");

        return 0;
    }

    public static void removeLogger(int logger) {
        log.warn("removeLogger not implemented yet.");

    }

    public static boolean waitForLoggerQueue(int inst, double timeout) {
        log.warn("waitForLoggerQueue not implemented yet.");

        return false;
    }

}