package frc.robot.simulator.sim.libgdx;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.utils.Clipboard;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This is a stub libgdx application so we can get that fancy joystick support with listeners but without
 * needing to create a whole lib gdx application. I probably could have gotten away with writing my own runnable
 * to query joysticks, but, meh.
 */
public class LibGDXApplicationStub implements Application {

    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public ApplicationListener getApplicationListener() {
        return null;
    }

    @Override
    public Graphics getGraphics() {
        return null;
    }

    @Override
    public Audio getAudio() {
        return null;
    }

    @Override
    public Input getInput() {
        return null;
    }

    @Override
    public Files getFiles() {
        return null;
    }

    @Override
    public Net getNet() {
        return null;
    }

    @Override
    public void log(String tag, String message) {

    }

    @Override
    public void log(String tag, String message, Throwable exception) {

    }

    @Override
    public void error(String tag, String message) {

    }

    @Override
    public void error(String tag, String message, Throwable exception) {

    }

    @Override
    public void debug(String tag, String message) {

    }

    @Override
    public void debug(String tag, String message, Throwable exception) {

    }

    @Override
    public void setLogLevel(int logLevel) {

    }

    @Override
    public int getLogLevel() {
        return 0;
    }

    @Override
    public void setApplicationLogger(ApplicationLogger applicationLogger) {

    }

    @Override
    public ApplicationLogger getApplicationLogger() {
        return null;
    }

    @Override
    public ApplicationType getType() {
        return ApplicationType.Desktop;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public long getJavaHeap() {
        return 0;
    }

    @Override
    public long getNativeHeap() {
        return 0;
    }

    @Override
    public Preferences getPreferences(String name) {
        return null;
    }

    @Override
    public Clipboard getClipboard() {
        return null;
    }

    @Override
    public void postRunnable(Runnable runnable) {
        // run the controller manager
        executor.execute(runnable);
    }

    @Override
    public void exit() {

    }

    @Override
    public void addLifecycleListener(LifecycleListener listener) {

    }

    @Override
    public void removeLifecycleListener(LifecycleListener listener) {

    }
}
