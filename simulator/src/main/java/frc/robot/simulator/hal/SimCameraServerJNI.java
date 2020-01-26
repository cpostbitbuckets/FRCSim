package frc.robot.simulator.hal;

import java.util.function.Consumer;

import frc.robot.simulator.sim.SimCameraServer;
import org.opencv.core.Core;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.UsbCameraInfo;
import edu.wpi.cscore.VideoEvent;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.wpiutil.RuntimeLoader;

/**
 * Sim JNI classes must be structured EXACTLY like their counterpart non-sim JNI classes. If a single field or method is added or removed, the ByteBuddy redefine will fail
 */
public class SimCameraServerJNI {
    static boolean libraryLoaded = false;
    static boolean cvLibraryLoaded = false;

    static RuntimeLoader<CameraServerJNI> loader = null;
    static RuntimeLoader<Core> cvLoader = null;

    static {
    }

    public static void forceLoad() {}

    //
    // Property Functions
    //
    public static int getPropertyKind(int property) {
        return SimCameraServer.getPropertyKind(property);
    }
    public static String getPropertyName(int property) {
        return SimCameraServer.getPropertyName(property);
    }
    public static int getProperty(int property) {
        return SimCameraServer.getProperty(property);
    }
    public static void setProperty(int property, int value) {
        SimCameraServer.setProperty(property, value);
    }
    public static int getPropertyMin(int property) {
        return SimCameraServer.getPropertyMin(property);
    }
    public static int getPropertyMax(int property) {
        return SimCameraServer.getPropertyMax(property);
    }
    public static int getPropertyStep(int property) {
        return SimCameraServer.getPropertyStep(property);
    }
    public static int getPropertyDefault(int property) {
        return SimCameraServer.getPropertyDefault(property);
    }
    public static String getStringProperty(int property) {
        return SimCameraServer.getStringProperty(property);
    }
    public static void setStringProperty(int property, String value) {
        SimCameraServer.setStringProperty(property, value);
    }
    public static String[] getEnumPropertyChoices(int property) {
        return SimCameraServer.getEnumPropertyChoices(property);
    }

    //
    // Source Creation Functions
    //
    public static int createUsbCameraDev(String name, int dev) {
        return SimCameraServer.createUsbCameraDev(name, dev);
    }
    public static int createUsbCameraPath(String name, String path) {
        return SimCameraServer.createUsbCameraPath(name, path);
    }
    public static int createHttpCamera(String name, String url, int kind) {
        return SimCameraServer.createHttpCamera(name, url, kind);
    }
    public static int createHttpCameraMulti(String name, String[] urls, int kind) {
        return SimCameraServer.createHttpCameraMulti(name, urls, kind);
    }
    public static int createCvSource(String name, int pixelFormat, int width, int height, int fps) {
        return SimCameraServer.createCvSource(name, pixelFormat, width, height, fps);
    }

    //
    // Source Functions
    //
    public static int getSourceKind(int source) {
        return SimCameraServer.getSourceKind(source);
    }
    public static String getSourceName(int source) {
        return SimCameraServer.getSourceName(source);
    }
    public static String getSourceDescription(int source) {
        return SimCameraServer.getSourceDescription(source);
    }
    public static long getSourceLastFrameTime(int source) {
        return SimCameraServer.getSourceLastFrameTime(source);
    }
    public static void setSourceConnectionStrategy(int source, int strategy) {
        SimCameraServer.setSourceConnectionStrategy(source, strategy);
    }
    public static boolean isSourceConnected(int source) {
        return SimCameraServer.isSourceConnected(source);
    }
    public static boolean isSourceEnabled(int source) {
        return SimCameraServer.isSourceEnabled(source);
    }
    public static int getSourceProperty(int source, String name) {
        return SimCameraServer.getSourceProperty(source, name);
    }
    public static int[] enumerateSourceProperties(int source) {
        return SimCameraServer.enumerateSourceProperties(source);
    }
    public static VideoMode getSourceVideoMode(int source) {
        return SimCameraServer.getSourceVideoMode(source);
    }
    public static boolean setSourceVideoMode(int source, int pixelFormat, int width, int height, int fps) {
        return SimCameraServer.setSourceVideoMode(source, pixelFormat, width, height, fps);
    }
    public static boolean setSourcePixelFormat(int source, int pixelFormat) {
        return SimCameraServer.setSourcePixelFormat(source, pixelFormat);
    }
    public static boolean setSourceResolution(int source, int width, int height) {
        return SimCameraServer.setSourceResolution(source, width, height);
    }
    public static boolean setSourceFPS(int source, int fps) {
        return SimCameraServer.setSourceFPS(source, fps);
    }
    public static boolean setSourceConfigJson(int source, String config) {
        return SimCameraServer.setSourceConfigJson(source, config);
    }
    public static String getSourceConfigJson(int source) {
        return SimCameraServer.getSourceConfigJson(source);
    }
    public static VideoMode[] enumerateSourceVideoModes(int source) {
        return SimCameraServer.enumerateSourceVideoModes(source);
    }
    public static int[] enumerateSourceSinks(int source) {
        return SimCameraServer.enumerateSourceSinks(source);
    }
    public static int copySource(int source) {
        return SimCameraServer.copySource(source);
    }
    public static void releaseSource(int source) {
        SimCameraServer.releaseSource(source);
    }

    //
    // Camera Source Common Property Fuctions
    //
    public static void setCameraBrightness(int source, int brightness) {
        SimCameraServer.setCameraBrightness(source, brightness);
    }
    public static int getCameraBrightness(int source) {
        return SimCameraServer.getCameraBrightness(source);
    }
    public static void setCameraWhiteBalanceAuto(int source) {
        SimCameraServer.setCameraWhiteBalanceAuto(source);
    }
    public static void setCameraWhiteBalanceHoldCurrent(int source) {
        SimCameraServer.setCameraWhiteBalanceHoldCurrent(source);
    }
    public static void setCameraWhiteBalanceManual(int source, int value) {
        SimCameraServer.setCameraWhiteBalanceManual(source, value);
    }
    public static void setCameraExposureAuto(int source) {
        SimCameraServer.setCameraExposureAuto(source);
    }
    public static void setCameraExposureHoldCurrent(int source) {
        SimCameraServer.setCameraExposureHoldCurrent(source);
    }
    public static void setCameraExposureManual(int source, int value) {
        SimCameraServer.setCameraExposureManual(source, value);
    }

    //
    // UsbCamera Source Functions
    //
    public static String getUsbCameraPath(int source) {
        return SimCameraServer.getUsbCameraPath(source);
    }
    public static UsbCameraInfo getUsbCameraInfo(int source) {
        return SimCameraServer.getUsbCameraInfo(source);
    }

    //
    // HttpCamera Source Functions
    //
    public static int getHttpCameraKind(int source) {
        return SimCameraServer.getHttpCameraKind(source);
    }
    public static void setHttpCameraUrls(int source, String[] urls) {
        SimCameraServer.setHttpCameraUrls(source, urls);
    }
    public static String[] getHttpCameraUrls(int source) {
        return SimCameraServer.getHttpCameraUrls(source);
    }

    //
    // OpenCV Source Functions
    //
    public static void putSourceFrame(int source, long imageNativeObj) {
        SimCameraServer.putSourceFrame(source, imageNativeObj);
    }
    public static void notifySourceError(int source, String msg) {
        SimCameraServer.notifySourceError(source, msg);
    }
    public static void setSourceConnected(int source, boolean connected) {
        SimCameraServer.setSourceConnected(source, connected);
    }
    public static void setSourceDescription(int source, String description) {
        SimCameraServer.setSourceDescription(source, description);
    }
    public static int createSourceProperty(int source, String name, int kind, int minimum, int maximum, int step, int defaultValue, int value) {
        return SimCameraServer.createSourceProperty(source, name, kind, minimum, maximum, step, defaultValue, value);
    }
    public static void setSourceEnumPropertyChoices(int source, int property, String[] choices) {
        SimCameraServer.setSourceEnumPropertyChoices(source, property, choices);
    }

    //
    // Sink Creation Functions
    //
    public static int createMjpegServer(String name, String listenAddress, int port) {
        return SimCameraServer.createMjpegServer(name, listenAddress, port);
    }
    public static int createCvSink(String name) {
        return SimCameraServer.createCvSink(name);
    }

    //
    // Sink Functions
    //
    public static int getSinkKind(int sink) {
        return SimCameraServer.getSinkKind(sink);
    }
    public static String getSinkName(int sink) {
        return SimCameraServer.getSinkName(sink);
    }
    public static String getSinkDescription(int sink) {
        return SimCameraServer.getSinkDescription(sink);
    }
    public static int getSinkProperty(int sink, String name) {
        return SimCameraServer.getSinkProperty(sink, name);
    }
    public static int[] enumerateSinkProperties(int sink) {
        return SimCameraServer.enumerateSinkProperties(sink);
    }
    public static boolean setSinkConfigJson(int sink, String config) {
        return SimCameraServer.setSinkConfigJson(sink, config);
    }
    public static String getSinkConfigJson(int sink) {
        return SimCameraServer.getSinkConfigJson(sink);
    }
    public static void setSinkSource(int sink, int source) {
        SimCameraServer.setSinkSource(sink, source);
    }
    public static int getSinkSourceProperty(int sink, String name) {
        return SimCameraServer.getSinkSourceProperty(sink, name);
    }
    public static int getSinkSource(int sink) {
        return SimCameraServer.getSinkSource(sink);
    }
    public static int copySink(int sink) {
        return SimCameraServer.copySink(sink);
    }
    public static void releaseSink(int sink) {
        SimCameraServer.releaseSink(sink);
    }

    //
    // MjpegServer Sink Functions
    //
    public static String getMjpegServerListenAddress(int sink) {
        return SimCameraServer.getMjpegServerListenAddress(sink);
    }
    public static int getMjpegServerPort(int sink) {
        return SimCameraServer.getMjpegServerPort(sink);
    }

    //
    // OpenCV Sink Functions
    //
    public static void setSinkDescription(int sink, String description) {
        SimCameraServer.setSinkDescription(sink, description);
    }
    public static long grabSinkFrame(int sink, long imageNativeObj) {
        return SimCameraServer.grabSinkFrame(sink, imageNativeObj);
    }
    public static long grabSinkFrameTimeout(int sink, long imageNativeObj, double timeout) {
        return SimCameraServer.grabSinkFrameTimeout(sink, imageNativeObj, timeout);
    }
    public static String getSinkError(int sink) {
        return SimCameraServer.getSinkError(sink);
    }
    public static void setSinkEnabled(int sink, boolean enabled) {
        SimCameraServer.setSinkEnabled(sink, enabled);
    }

    //
    // Listener Functions
    //
    public static int addListener(Consumer<VideoEvent> listener,
                                  int eventMask, boolean immediateNotify) {
        return SimCameraServer.addListener(listener, eventMask, immediateNotify);
    }

    public static void removeListener(int handle) {
        SimCameraServer.removeListener(handle);
    }

    public static void setTelemetryPeriod(double seconds) {
        SimCameraServer.setTelemetryPeriod(seconds);
    }
    public static double getTelemetryElapsedTime() {
        return SimCameraServer.getTelemetryElapsedTime();
    }
    public static long getTelemetryValue(int handle, int kind) {
        return SimCameraServer.getTelemetryValue(handle, kind);
    }
    public static long getTelemetryValue(int handle, CameraServerJNI.TelemetryKind kind) {
        return getTelemetryValue(handle, kind.getValue());
    }
    public static double getTelemetryAverageValue(int handle, int kind) {
        return SimCameraServer.getTelemetryAverageValue(handle, kind);
    }
    public static double getTelemetryAverageValue(int handle, CameraServerJNI.TelemetryKind kind) {
        return getTelemetryAverageValue(handle, kind.getValue());
    }

    public static void setLogger(CameraServerJNI.LoggerFunction func, int minLevel) {
        SimCameraServer.setLogger(func, minLevel);
    }

    //
    // Utility Functions
    //
    public static UsbCameraInfo[] enumerateUsbCameras() {
        return SimCameraServer.enumerateUsbCameras();
    }

    public static int[] enumerateSources() {
        return SimCameraServer.enumerateSources();
    }

    public static int[] enumerateSinks() {
        return SimCameraServer.enumerateSinks();
    }

    public static String getHostname() {
        return SimCameraServer.getHostname();
    }

    public static String[] getNetworkInterfaces() {
        return SimCameraServer.getNetworkInterfaces();
    }
}
