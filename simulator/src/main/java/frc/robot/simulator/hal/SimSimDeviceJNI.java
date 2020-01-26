package frc.robot.simulator.hal;

import edu.wpi.first.hal.HALValue;
import edu.wpi.first.hal.JNIWrapper;
import frc.robot.simulator.sim.SimSimDevice;

public class SimSimDeviceJNI extends JNIWrapper {
    /**
     * Creates a simulated device.
     *
     * <p>The device name must be unique.  0 is returned if the device name
     * already exists.  If multiple instances of the same device are desired,
     * recommend appending the instance/unique identifer in brackets to the base
     * name, e.g. "device[1]".
     *
     * <p>0 is returned if not in simulation.
     *
     * @param name device name
     * @return simulated device handle
     */
    public static int createSimDevice(String name) {
        return SimSimDevice.createSimDevice(name);
    }

    /**
     * Frees a simulated device.
     *
     * <p>This also allows the same device name to be used again.
     * This also frees all the simulated values created on the device.
     *
     * @param handle simulated device handle
     */
    public static void freeSimDevice(int handle) {
        SimSimDevice.freeSimDevice(handle);
    }

    private static native int createSimValueNative(int device, String name, boolean readonly,
                                                   int type, long value1, double value2);
    
    /**
     * Creates a value on a simulated device.
     *
     * <p>Returns 0 if not in simulation; this can be used to avoid calls
     * to Set/Get functions.
     *
     * @param device       simulated device handle
     * @param name         value name
     * @param readonly     if the value should not be written from simulation side
     * @param initialValue initial value
     * @return simulated value handle
     */
    public static int createSimValue(int device, String name, boolean readonly,
                                     HALValue initialValue) {
        return createSimValueNative(device, name, readonly, initialValue.getType(),
                initialValue.getNativeLong(), initialValue.getNativeDouble());
    }

    /**
     * Creates a double value on a simulated device.
     *
     * <p>Returns 0 if not in simulation; this can be used to avoid calls
     * to Set/Get functions.
     *
     * @param device       simulated device handle
     * @param name         value name
     * @param readonly     if the value should not be written from simulation side
     * @param initialValue initial value
     * @return simulated value handle
     */
    public static int createSimValueDouble(int device, String name, boolean readonly,
                                           double initialValue) {
        return createSimValueNative(device, name, readonly, HALValue.kDouble, 0, initialValue);
    }

    /**
     * Creates an enumerated value on a simulated device.
     *
     * <p>Enumerated values are always in the range 0 to numOptions-1.
     *
     * <p>Returns 0 if not in simulation; this can be used to avoid calls
     * to Set/Get functions.
     *
     * @param device       simulated device handle
     * @param name         value name
     * @param readonly     if the value should not be written from simulation side
     * @param options      array of option descriptions
     * @param initialValue initial value (selection)
     * @return simulated value handle
     */
    public static int createSimValueEnum(int device, String name, boolean readonly,
                                         String[] options, int initialValue) {
        return SimSimDevice.createSimValueEnum(device, name, readonly, options, initialValue);
    }

    /**
     * Creates a boolean value on a simulated device.
     *
     * <p>Returns 0 if not in simulation; this can be used to avoid calls
     * to Set/Get functions.
     *
     * @param device       simulated device handle
     * @param name         value name
     * @param readonly     if the value should not be written from simulation side
     * @param initialValue initial value
     * @return simulated value handle
     */
    public static int createSimValueBoolean(int device, String name, boolean readonly,
                                            boolean initialValue) {
        return createSimValueNative(device, name, readonly, HALValue.kBoolean,
                initialValue ? 1 : 0, 0.0);
    }

    /**
     * Gets a simulated value.
     *
     * @param handle simulated value handle
     * @return The current value
     */
    public static HALValue getSimValue(int handle) {
        return SimSimDevice.getSimValue(handle);
    }

    /**
     * Gets a simulated value (double).
     *
     * @param handle simulated value handle
     * @return The current value
     */
    public static double getSimValueDouble(int handle) {
        return SimSimDevice.getSimValueDouble(handle);
    }

    /**
     * Gets a simulated value (enum).
     *
     * @param handle simulated value handle
     * @return The current value
     */
    public static int getSimValueEnum(int handle) {
        return SimSimDevice.getSimValueEnum(handle);
    }

    /**
     * Gets a simulated value (boolean).
     *
     * @param handle simulated value handle
     * @return The current value
     */
    public static boolean getSimValueBoolean(int handle) {
        return SimSimDevice.getSimValueBoolean(handle);
    }

    private static native void setSimValueNative(int handle, int type, long value1, double value2);

    /**
     * Sets a simulated value.
     *
     * @param handle simulated value handle
     * @param value  the value to set
     */
    public static void setSimValue(int handle, HALValue value) {
        setSimValueNative(handle, value.getType(), value.getNativeLong(), value.getNativeDouble());
    }

    /**
     * Sets a simulated value (double).
     *
     * @param handle simulated value handle
     * @param value  the value to set
     */
    public static void setSimValueDouble(int handle, double value) {
        setSimValueNative(handle, HALValue.kDouble, 0, value);
    }

    /**
     * Sets a simulated value (enum).
     *
     * @param handle simulated value handle
     * @param value  the value to set
     */
    public static void setSimValueEnum(int handle, int value) {
        setSimValueNative(handle, HALValue.kEnum, value, 0.0);
    }

    /**
     * Sets a simulated value (boolean).
     *
     * @param handle simulated value handle
     * @param value  the value to set
     */
    public static void setSimValueBoolean(int handle, boolean value) {
        setSimValueNative(handle, HALValue.kBoolean, value ? 1 : 0, 0.0);
    }
}

