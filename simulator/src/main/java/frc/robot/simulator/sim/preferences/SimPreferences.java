package frc.robot.simulator.sim.preferences;

import edu.wpi.first.networktables.NetworkTable;

import java.util.Collection;
import java.util.Collections;

/**
 * A blank preferences class to override the regular Preferences class so we don't try and write
 * preferences to the network tables. This causes the NT to constantly try and write a preferences file
 * which fails and spams the logs with errors
 */
public final class SimPreferences {

    private static final String TABLE_NAME = "SimPreferences";
    private static SimPreferences instance;
    private final NetworkTable m_table;

    public static synchronized SimPreferences getInstance() {
        if (instance == null) {
            instance = new SimPreferences();
        }
        return instance;
    }

    private SimPreferences() {
        m_table = null;
    }

    public Collection<String> getKeys() {
        return Collections.emptyList();
    }

    public void putString(String key, String value) {
    }

    public void putInt(String key, int value) {
    }

    public void putDouble(String key, double value) {
    }

    public void putFloat(String key, float value) {
    }

    public void putBoolean(String key, boolean value) {
    }

    public void putLong(String key, long value) {
    }

    public boolean containsKey(String key) {
        return false;
    }

    public void remove(String key) {
    }

    public void removeAll() {
    }

    public String getString(String key, String backup) {
        return backup;
    }

    public int getInt(String key, int backup) {
        return backup;
    }

    public double getDouble(String key, double backup) {
        return backup;
    }

    public boolean getBoolean(String key, boolean backup) {
        return backup;
    }

    public float getFloat(String key, float backup) {
        return backup;
    }

    public long getLong(String key, long backup) {
        return backup;
    }
}
