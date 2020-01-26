package frc.robot.simulator.hal;

import java.nio.ByteBuffer;

import edu.wpi.first.networktables.ConnectionInfo;
import edu.wpi.first.networktables.ConnectionNotification;
import edu.wpi.first.networktables.EntryInfo;
import edu.wpi.first.networktables.EntryNotification;
import edu.wpi.first.networktables.LogMessage;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.NetworkTablesJNI;
import edu.wpi.first.networktables.PersistentException;
import edu.wpi.first.networktables.RpcAnswer;
import edu.wpi.first.wpiutil.RuntimeLoader;
import frc.robot.simulator.sim.SimNetworkTables;

/**
 * This is a networktables JNI sim, but we don't use it. We use the actual network tables JNI so we can develop locally
 * with outlineviewer and shuffleboard
 */
public final class SimNetworkTablesJNI {
    static boolean libraryLoaded = false;
    static RuntimeLoader<NetworkTablesJNI> loader = null;

    static {
    }

    public static int getDefaultInstance() {
        return SimNetworkTables.getDefaultInstance();
    }
    public static int createInstance() {
        return SimNetworkTables.createInstance();
    }
    public static void destroyInstance(int inst) {
        SimNetworkTables.destroyInstance(inst);
    }
    public static int getInstanceFromHandle(int handle) {
        return SimNetworkTables.getInstanceFromHandle(handle);
    }

    public static int getEntry(int inst, String key) {
        return SimNetworkTables.getEntry(inst, key);
    }
    public static int[] getEntries(int inst, String prefix, int types) {
        return SimNetworkTables.getEntries(inst, prefix, types);
    }
    public static String getEntryName(int entry) {
        return SimNetworkTables.getEntryName(entry);
    }
    public static long getEntryLastChange(int entry) {
        return SimNetworkTables.getEntryLastChange(entry);
    }

    public static int getType(int entry) {
        return SimNetworkTables.getType(entry);
    }

    public static boolean setBoolean(int entry, long time, boolean value, boolean force) {
        return SimNetworkTables.setBoolean(entry, time, value, force);
    }
    public static boolean setDouble(int entry, long time, double value, boolean force) {
        return SimNetworkTables.setDouble(entry, time, value, force);
    }
    public static boolean setString(int entry, long time, String value, boolean force) {
        return SimNetworkTables.setString(entry, time, value, force);
    }
    public static boolean setRaw(int entry, long time, byte[] value, boolean force) {
        return SimNetworkTables.setRaw(entry, time, value, force);
    }
    public static boolean setRaw(int entry, long time, ByteBuffer value, int len, boolean force) {
        return SimNetworkTables.setRaw(entry, time, value, len, force);
    }
    public static boolean setBooleanArray(int entry, long time, boolean[] value, boolean force) {
        return SimNetworkTables.setBooleanArray(entry, time, value, force);
    }
    public static boolean setDoubleArray(int entry, long time, double[] value, boolean force) {
        return SimNetworkTables.setDoubleArray(entry, time, value, force);
    }
    public static boolean setStringArray(int entry, long time, String[] value, boolean force) {
        return SimNetworkTables.setStringArray(entry, time, value, force);
    }

    public static NetworkTableValue getValue(int entry) {
        return SimNetworkTables.getValue(entry);
    }

    public static boolean getBoolean(int entry, boolean defaultValue) {
        return SimNetworkTables.getBoolean(entry, defaultValue);
    }
    public static double getDouble(int entry, double defaultValue) {
        return SimNetworkTables.getDouble(entry, defaultValue);
    }
    public static String getString(int entry, String defaultValue) {
        return SimNetworkTables.getString(entry, defaultValue);
    }
    public static byte[] getRaw(int entry, byte[] defaultValue) {
        return SimNetworkTables.getRaw(entry, defaultValue);
    }
    public static boolean[] getBooleanArray(int entry, boolean[] defaultValue) {
        return SimNetworkTables.getBooleanArray(entry, defaultValue);
    }
    public static double[] getDoubleArray(int entry, double[] defaultValue) {
        return SimNetworkTables.getDoubleArray(entry, defaultValue);
    }
    public static String[] getStringArray(int entry, String[] defaultValue) {
        return SimNetworkTables.getStringArray(entry, defaultValue);
    }
    public static boolean setDefaultBoolean(int entry, long time, boolean defaultValue) {
        return SimNetworkTables.setDefaultBoolean(entry, time, defaultValue);
    }

    public static boolean setDefaultDouble(int entry, long time, double defaultValue) {
        return SimNetworkTables.setDefaultDouble(entry, time, defaultValue);
    }
    public static boolean setDefaultString(int entry, long time, String defaultValue) {
        return SimNetworkTables.setDefaultString(entry, time, defaultValue);
    }
    public static boolean setDefaultRaw(int entry, long time, byte[] defaultValue) {
        return SimNetworkTables.setDefaultRaw(entry, time, defaultValue);
    }
    public static boolean setDefaultBooleanArray(int entry, long time, boolean[] defaultValue) {
        return SimNetworkTables.setDefaultBooleanArray(entry, time, defaultValue);
    }
    public static boolean setDefaultDoubleArray(int entry, long time, double[] defaultValue) {
        return SimNetworkTables.setDefaultDoubleArray(entry, time, defaultValue);
    }
    public static boolean setDefaultStringArray(int entry, long time, String[] defaultValue) {
        return SimNetworkTables.setDefaultStringArray(entry, time, defaultValue);
    }

    public static void setEntryFlags(int entry, int flags) {
        SimNetworkTables.setEntryFlags(entry, flags);
    }
    public static int getEntryFlags(int entry) {
        return SimNetworkTables.getEntryFlags(entry);
    }

    public static void deleteEntry(int entry) {
        SimNetworkTables.deleteEntry(entry);
    }

    public static void deleteAllEntries(int inst) {
        SimNetworkTables.deleteAllEntries(inst);
    }

    public static EntryInfo getEntryInfoHandle(NetworkTableInstance inst, int entry) {
        return SimNetworkTables.getEntryInfoHandle(inst, entry);
    }
    public static EntryInfo[] getEntryInfo(NetworkTableInstance instObject, int inst, String prefix, int types) {
        return SimNetworkTables.getEntryInfo(instObject, inst, prefix, types);
    }

    public static int createEntryListenerPoller(int inst) {
        return SimNetworkTables.createEntryListenerPoller(inst);
    }
    public static void destroyEntryListenerPoller(int poller) {
        SimNetworkTables.destroyEntryListenerPoller(poller);
    }
    public static int addPolledEntryListener(int poller, String prefix, int flags) {
        return SimNetworkTables.addPolledEntryListener(poller, prefix, flags);
    }
    public static int addPolledEntryListener(int poller, int entry, int flags) {
        return SimNetworkTables.addPolledEntryListener(poller, entry, flags);
    }
    public static native EntryNotification[] pollEntryListener(NetworkTableInstance inst, int poller) throws InterruptedException;
    public static native EntryNotification[] pollEntryListenerTimeout(NetworkTableInstance inst, int poller, double timeout) throws InterruptedException;
    public static native void cancelPollEntryListener(int poller);
    public static void removeEntryListener(int entryListener) {
        SimNetworkTables.removeEntryListener(entryListener);
    }
    public static boolean waitForEntryListenerQueue(int inst, double timeout) {
        return SimNetworkTables.waitForEntryListenerQueue(inst, timeout);
    }

    public static int createConnectionListenerPoller(int inst) {
        return SimNetworkTables.createConnectionListenerPoller(inst);
    }
    public static void destroyConnectionListenerPoller(int poller) {
        SimNetworkTables.destroyConnectionListenerPoller(poller);
    }
    public static int addPolledConnectionListener(int poller, boolean immediateNotify) {
        return SimNetworkTables.addPolledConnectionListener(poller, immediateNotify);
    }
    public static native ConnectionNotification[] pollConnectionListener(NetworkTableInstance inst, int poller) throws InterruptedException;
    public static native ConnectionNotification[] pollConnectionListenerTimeout(NetworkTableInstance inst, int poller, double timeout) throws InterruptedException;
    public static native void cancelPollConnectionListener(int poller);
    public static void removeConnectionListener(int connListener) {
        SimNetworkTables.removeConnectionListener(connListener);
    }
    public static boolean waitForConnectionListenerQueue(int inst, double timeout) {
        return SimNetworkTables.waitForConnectionListenerQueue(inst, timeout);
    }

    public static int createRpcCallPoller(int inst) {
        return SimNetworkTables.createRpcCallPoller(inst);
    }
    public static void destroyRpcCallPoller(int poller) {
        SimNetworkTables.destroyRpcCallPoller(poller);
    }
    public static void createPolledRpc(int entry, byte[] def, int poller) {
        SimNetworkTables.createPolledRpc(entry, def, poller);
    }
    public static native RpcAnswer[] pollRpc(NetworkTableInstance inst, int poller) throws InterruptedException;
    public static native RpcAnswer[] pollRpcTimeout(NetworkTableInstance inst, int poller, double timeout) throws InterruptedException;
    public static native void cancelPollRpc(int poller);
    public static boolean waitForRpcCallQueue(int inst, double timeout) {
        return SimNetworkTables.waitForRpcCallQueue(inst, timeout);
    }
    public static boolean postRpcResponse(int entry, int call, byte[] result) {
        return SimNetworkTables.postRpcResponse(entry, call, result);
    }
    public static int callRpc(int entry, byte[] params) {
        return SimNetworkTables.callRpc(entry, params);
    }
    public static byte[] getRpcResult(int entry, int call) {
        return SimNetworkTables.getRpcResult(entry, call);
    }
    public static byte[] getRpcResult(int entry, int call, double timeout) {
        return SimNetworkTables.getRpcResult(entry, call, timeout);
    }
    public static void cancelRpcResult(int entry, int call) {
        SimNetworkTables.cancelRpcResult(entry, call);
    }

    public static byte[] getRpc(int entry, byte[] defaultValue) {
        return SimNetworkTables.getRpc(entry, defaultValue);
    }

    public static void setNetworkIdentity(int inst, String name) {
        SimNetworkTables.setNetworkIdentity(inst, name);
    }
    public static int getNetworkMode(int inst) {
        return SimNetworkTables.getNetworkMode(inst);
    }
    public static void startServer(int inst, String persistFilename, String listenAddress, int port) {
        SimNetworkTables.startServer(inst, persistFilename, listenAddress, port);
    }
    public static void stopServer(int inst) {
        SimNetworkTables.stopServer(inst);
    }
    public static void startClient(int inst) {
        SimNetworkTables.startClient(inst);
    }
    public static void startClient(int inst, String serverName, int port) {
        SimNetworkTables.startClient(inst, serverName, port);
    }
    public static void startClient(int inst, String[] serverNames, int[] ports) {
        SimNetworkTables.startClient(inst, serverNames, ports);
    }
    public static void startClientTeam(int inst, int team, int port) {
        SimNetworkTables.startClientTeam(inst, team, port);
    }
    public static void stopClient(int inst) {
        SimNetworkTables.stopClient(inst);
    }
    public static void setServer(int inst, String serverName, int port) {
        SimNetworkTables.setServer(inst, serverName, port);
    }
    public static void setServer(int inst, String[] serverNames, int[] ports) {
        SimNetworkTables.setServer(inst, serverNames, ports);
    }
    public static void setServerTeam(int inst, int team, int port) {
        SimNetworkTables.setServerTeam(inst, team, port);
    }
    public static void startDSClient(int inst, int port) {
        SimNetworkTables.startDSClient(inst, port);
    }
    public static void stopDSClient(int inst) {
        SimNetworkTables.stopDSClient(inst);
    }
    public static void setUpdateRate(int inst, double interval) {
        SimNetworkTables.setUpdateRate(inst, interval);
    }

    public static void flush(int inst) {
        SimNetworkTables.flush(inst);
    }

    public static ConnectionInfo[] getConnections(int inst) {
        return SimNetworkTables.getConnections(inst);
    }

    public static boolean isConnected(int inst) {
        return SimNetworkTables.isConnected(inst);
    }

    public static native void savePersistent(int inst, String filename) throws PersistentException;
    public static native String[] loadPersistent(int inst, String filename) throws PersistentException;  // returns warnings

    public static native void saveEntries(int inst, String filename, String prefix) throws PersistentException;
    public static native String[] loadEntries(int inst, String filename, String prefix) throws PersistentException;  // returns warnings

    public static native long now();

    public static int createLoggerPoller(int inst) {
        return SimNetworkTables.createLoggerPoller(inst);
    }
    public static void destroyLoggerPoller(int poller) {
        SimNetworkTables.destroyLoggerPoller(poller);
    }
    public static int addPolledLogger(int poller, int minLevel, int maxLevel) {
        return SimNetworkTables.addPolledLogger(poller, minLevel, maxLevel);
    }
    public static native LogMessage[] pollLogger(NetworkTableInstance inst, int poller) throws InterruptedException;
    public static native LogMessage[] pollLoggerTimeout(NetworkTableInstance inst, int poller, double timeout) throws InterruptedException;
    public static native void cancelPollLogger(int poller);
    public static void removeLogger(int logger) {
        SimNetworkTables.removeLogger(logger);
    }
    public static boolean waitForLoggerQueue(int inst, double timeout) {
        return SimNetworkTables.waitForLoggerQueue(inst, timeout);
    }
}

