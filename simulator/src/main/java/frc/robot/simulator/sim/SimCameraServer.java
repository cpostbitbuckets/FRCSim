
package frc.robot.simulator.sim;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.UsbCameraInfo;
import edu.wpi.cscore.VideoEvent;
import edu.wpi.cscore.VideoMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class SimCameraServer {
    private static final Logger log = LoggerFactory.getLogger(SimCameraServer.class);


    public static void forceLoad() {
        log.warn("forceLoad not implemented yet.");

    }

    public static int getPropertyKind(int property) {
        log.warn("getPropertyKind not implemented yet.");

        return 0;
    }

    public static String getPropertyName(int property) {
        log.warn("getPropertyName not implemented yet.");

        return null;
    }

    public static int getProperty(int property) {
        log.warn("getProperty not implemented yet.");

        return 0;
    }

    public static void setProperty(int property, int value) {
        log.warn("setProperty not implemented yet.");

    }

    public static int getPropertyMin(int property) {
        log.warn("getPropertyMin not implemented yet.");

        return 0;
    }

    public static int getPropertyMax(int property) {
        log.warn("getPropertyMax not implemented yet.");

        return 0;
    }

    public static int getPropertyStep(int property) {
        log.warn("getPropertyStep not implemented yet.");

        return 0;
    }

    public static int getPropertyDefault(int property) {
        log.warn("getPropertyDefault not implemented yet.");

        return 0;
    }

    public static String getStringProperty(int property) {
        log.warn("getStringProperty not implemented yet.");

        return null;
    }

    public static void setStringProperty(int property, String value) {
        log.warn("setStringProperty not implemented yet.");

    }

    public static String[] getEnumPropertyChoices(int property) {
        log.warn("getEnumPropertyChoices not implemented yet.");

        return null;
    }

    public static int createUsbCameraDev(String name, int dev) {
        log.warn("createUsbCameraDev not implemented yet.");

        return 0;
    }

    public static int createUsbCameraPath(String name, String path) {
        log.warn("createUsbCameraPath not implemented yet.");

        return 0;
    }

    public static int createHttpCamera(String name, String url, int kind) {
        log.warn("createHttpCamera not implemented yet.");

        return 0;
    }

    public static int createHttpCameraMulti(String name, String[] urls, int kind) {
        log.warn("createHttpCameraMulti not implemented yet.");

        return 0;
    }

    public static int createCvSource(String name, int pixelFormat, int width, int height, int fps) {
        log.warn("createCvSource not implemented yet.");

        return 0;
    }

    public static int getSourceKind(int source) {
        log.warn("getSourceKind not implemented yet.");

        return 0;
    }

    public static String getSourceName(int source) {
        log.warn("getSourceName not implemented yet.");

        return null;
    }

    public static String getSourceDescription(int source) {
        log.warn("getSourceDescription not implemented yet.");

        return null;
    }

    public static long getSourceLastFrameTime(int source) {
        log.warn("getSourceLastFrameTime not implemented yet.");

        return 0;
    }

    public static void setSourceConnectionStrategy(int source, int strategy) {
        log.warn("setSourceConnectionStrategy not implemented yet.");

    }

    public static boolean isSourceConnected(int source) {
        log.warn("isSourceConnected not implemented yet.");

        return false;
    }

    public static boolean isSourceEnabled(int source) {
        log.warn("isSourceEnabled not implemented yet.");

        return false;
    }

    public static int getSourceProperty(int source, String name) {
        log.warn("getSourceProperty not implemented yet.");

        return 0;
    }

    public static int[] enumerateSourceProperties(int source) {
        log.warn("enumerateSourceProperties not implemented yet.");

        return null;
    }

    public static VideoMode getSourceVideoMode(int source) {
        log.warn("getSourceVideoMode not implemented yet.");

        return null;
    }

    public static boolean setSourceVideoMode(int source, int pixelFormat, int width, int height, int fps) {
        log.warn("setSourceVideoMode not implemented yet.");

        return false;
    }

    public static boolean setSourcePixelFormat(int source, int pixelFormat) {
        log.warn("setSourcePixelFormat not implemented yet.");

        return false;
    }

    public static boolean setSourceResolution(int source, int width, int height) {
        log.warn("setSourceResolution not implemented yet.");

        return false;
    }

    public static boolean setSourceFPS(int source, int fps) {
        log.warn("setSourceFPS not implemented yet.");

        return false;
    }

    public static boolean setSourceConfigJson(int source, String config) {
        log.warn("setSourceConfigJson not implemented yet.");

        return false;
    }

    public static String getSourceConfigJson(int source) {
        log.warn("getSourceConfigJson not implemented yet.");

        return null;
    }

    public static VideoMode[] enumerateSourceVideoModes(int source) {
        log.warn("enumerateSourceVideoModes not implemented yet.");

        return null;
    }

    public static int[] enumerateSourceSinks(int source) {
        log.warn("enumerateSourceSinks not implemented yet.");

        return null;
    }

    public static int copySource(int source) {
        log.warn("copySource not implemented yet.");

        return 0;
    }

    public static void releaseSource(int source) {
        log.warn("releaseSource not implemented yet.");

    }

    public static void setCameraBrightness(int source, int brightness) {
        log.warn("setCameraBrightness not implemented yet.");

    }

    public static int getCameraBrightness(int source) {
        log.warn("getCameraBrightness not implemented yet.");

        return 0;
    }

    public static void setCameraWhiteBalanceAuto(int source) {
        log.warn("setCameraWhiteBalanceAuto not implemented yet.");

    }

    public static void setCameraWhiteBalanceHoldCurrent(int source) {
        log.warn("setCameraWhiteBalanceHoldCurrent not implemented yet.");

    }

    public static void setCameraWhiteBalanceManual(int source, int value) {
        log.warn("setCameraWhiteBalanceManual not implemented yet.");

    }

    public static void setCameraExposureAuto(int source) {
        log.warn("setCameraExposureAuto not implemented yet.");

    }

    public static void setCameraExposureHoldCurrent(int source) {
        log.warn("setCameraExposureHoldCurrent not implemented yet.");

    }

    public static void setCameraExposureManual(int source, int value) {
        log.warn("setCameraExposureManual not implemented yet.");

    }

    public static String getUsbCameraPath(int source) {
        log.warn("getUsbCameraPath not implemented yet.");

        return null;
    }

    public static UsbCameraInfo getUsbCameraInfo(int source) {
        log.warn("getUsbCameraInfo not implemented yet.");

        return null;
    }

    public static int getHttpCameraKind(int source) {
        log.warn("getHttpCameraKind not implemented yet.");

        return 0;
    }

    public static void setHttpCameraUrls(int source, String[] urls) {
        log.warn("setHttpCameraUrls not implemented yet.");

    }

    public static String[] getHttpCameraUrls(int source) {
        log.warn("getHttpCameraUrls not implemented yet.");

        return null;
    }

    public static void putSourceFrame(int source, long imageNativeObj) {
        log.warn("putSourceFrame not implemented yet.");

    }

    public static void notifySourceError(int source, String msg) {
        log.warn("notifySourceError not implemented yet.");

    }

    public static void setSourceConnected(int source, boolean connected) {
        log.warn("setSourceConnected not implemented yet.");

    }

    public static void setSourceDescription(int source, String description) {
        log.warn("setSourceDescription not implemented yet.");

    }

    public static int createSourceProperty(int source, String name, int kind, int minimum, int maximum, int step, int defaultValue, int value) {
        log.warn("createSourceProperty not implemented yet.");

        return 0;
    }

    public static void setSourceEnumPropertyChoices(int source, int property, String[] choices) {
        log.warn("setSourceEnumPropertyChoices not implemented yet.");

    }

    public static int createMjpegServer(String name, String listenAddress, int port) {
        log.warn("createMjpegServer not implemented yet.");

        return 0;
    }

    public static int createCvSink(String name) {
        log.warn("createCvSink not implemented yet.");

        return 0;
    }

    public static int getSinkKind(int sink) {
        log.warn("getSinkKind not implemented yet.");

        return 0;
    }

    public static String getSinkName(int sink) {
        log.warn("getSinkName not implemented yet.");

        return null;
    }

    public static String getSinkDescription(int sink) {
        log.warn("getSinkDescription not implemented yet.");

        return null;
    }

    public static int getSinkProperty(int sink, String name) {
        log.warn("getSinkProperty not implemented yet.");

        return 0;
    }

    public static int[] enumerateSinkProperties(int sink) {
        log.warn("enumerateSinkProperties not implemented yet.");

        return null;
    }

    public static boolean setSinkConfigJson(int sink, String config) {
        log.warn("setSinkConfigJson not implemented yet.");

        return false;
    }

    public static String getSinkConfigJson(int sink) {
        log.warn("getSinkConfigJson not implemented yet.");

        return null;
    }

    public static void setSinkSource(int sink, int source) {
        log.warn("setSinkSource not implemented yet.");

    }

    public static int getSinkSourceProperty(int sink, String name) {
        log.warn("getSinkSourceProperty not implemented yet.");

        return 0;
    }

    public static int getSinkSource(int sink) {
        log.warn("getSinkSource not implemented yet.");

        return 0;
    }

    public static int copySink(int sink) {
        log.warn("copySink not implemented yet.");

        return 0;
    }

    public static void releaseSink(int sink) {
        log.warn("releaseSink not implemented yet.");

    }

    public static String getMjpegServerListenAddress(int sink) {
        log.warn("getMjpegServerListenAddress not implemented yet.");

        return null;
    }

    public static int getMjpegServerPort(int sink) {
        log.warn("getMjpegServerPort not implemented yet.");

        return 0;
    }

    public static void setSinkDescription(int sink, String description) {
        log.warn("setSinkDescription not implemented yet.");

    }

    public static long grabSinkFrame(int sink, long imageNativeObj) {
        log.warn("grabSinkFrame not implemented yet.");

        return 0;
    }

    public static long grabSinkFrameTimeout(int sink, long imageNativeObj, double timeout) {
        log.warn("grabSinkFrameTimeout not implemented yet.");

        return 0;
    }

    public static String getSinkError(int sink) {
        log.warn("getSinkError not implemented yet.");

        return null;
    }

    public static void setSinkEnabled(int sink, boolean enabled) {
        log.warn("setSinkEnabled not implemented yet.");

    }

    public static int addListener(Consumer<VideoEvent> listener,
                                  int eventMask, boolean immediateNotify) {
        log.warn("addListener not implemented yet.");

        return 0;
    }

    public static void removeListener(int handle) {
        log.warn("removeListener not implemented yet.");

    }

    public static void setTelemetryPeriod(double seconds) {
        log.warn("setTelemetryPeriod not implemented yet.");

    }

    public static double getTelemetryElapsedTime() {
        log.warn("getTelemetryElapsedTime not implemented yet.");

        return 0;
    }

    public static long getTelemetryValue(int handle, int kind) {
        log.warn("getTelemetryValue not implemented yet.");

        return 0;
    }

    public static long getTelemetryValue(int handle, CameraServerJNI.TelemetryKind kind) {
        log.warn("getTelemetryValue not implemented yet.");

        return 0;
    }

    public static double getTelemetryAverageValue(int handle, int kind) {
        log.warn("getTelemetryAverageValue not implemented yet.");

        return 0;
    }

    public static double getTelemetryAverageValue(int handle, CameraServerJNI.TelemetryKind kind) {
        log.warn("getTelemetryAverageValue not implemented yet.");

        return 0;
    }

    public static void setLogger(CameraServerJNI.LoggerFunction func, int minLevel) {
        log.warn("setLogger not implemented yet.");

    }

    public static UsbCameraInfo[] enumerateUsbCameras() {
        log.warn("enumerateUsbCameras not implemented yet.");

        return null;
    }

    public static int[] enumerateSources() {
        log.warn("enumerateSources not implemented yet.");

        return null;
    }

    public static int[] enumerateSinks() {
        log.warn("enumerateSinks not implemented yet.");

        return null;
    }

    public static String getHostname() {
        log.warn("getHostname not implemented yet.");

        return null;
    }

    public static String[] getNetworkInterfaces() {
        log.warn("getNetworkInterfaces not implemented yet.");

        return null;
    }

}

