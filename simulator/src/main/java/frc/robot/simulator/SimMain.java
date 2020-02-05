package frc.robot.simulator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.first.hal.*;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.simulator.hal.*;
import frc.robot.simulator.sim.Simulator;
import frc.robot.simulator.sim.SimulatorModule;
import frc.robot.simulator.sim.SimulatorSettings;
import frc.robot.simulator.sim.utils.VendorUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.dynamic.scaffold.TypeValidation;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.pool.TypePool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class SimMain {
    private static final Logger log = LoggerFactory.getLogger(SimMain.class);

    public static void main(String[] args) throws InterruptedException, IOException {
        // map all the existing JNIs onto simulator versions
        redifineJNIs();

        // Create a guice injector for dependency injection
        Injector injector = Guice.createInjector(new SimulatorModule());

        // instantiate a settings and a simulator object
        SimulatorSettings simulatorSettings = injector.getInstance(SimulatorSettings.class);
        Simulator simulator = injector.getInstance(Simulator.class);

        simulator.start();

        try {
            // if we have a local network table running, connect to it
            connectToLocalNetworkTable();

            // start the robot code
            // call the RobotBase.startRobot like we do in the normal Robot Main.main()
            RobotBase.startRobot(() -> {
                try {
                    return (RobotBase) Class.forName(simulatorSettings.getRobotClass()).getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    log.error("Failed to instantiate robot class: " + simulatorSettings.getRobotClass(), e);
                }
                throw new RuntimeException("Failed to instantiate robot class: " + simulatorSettings.getRobotClass() + " exiting");
            });
        } finally {
            // shut down after the robot finishes, though it should just run until we cancel out
            simulator.stop();
        }
    }

    /**
     * Connect to a local network table instance (running from outline viewer) if it is there, otherwise the robot will create
     * its own network table
     * @throws InterruptedException
     */
    private static void connectToLocalNetworkTable() throws InterruptedException {
        NetworkTableInstance localNetworkTable = NetworkTableInstance.create();
        localNetworkTable.setServer("localhost", NetworkTableInstance.kDefaultPort);
        if (localNetworkTable.isValid()) {
            localNetworkTable.startClient();

            // this stupid startClient() function returns before it actually CONNECTS to the server, but if we wait
            // half a second and there is an outlineviewer server, isConnected() below returns true
            Thread.sleep(500);

            // if we have a local networktable running (through outlineviewer), connect to that
            if (localNetworkTable.isConnected()) {
                NetworkTableInstance.getDefault().setServer("localhost", NetworkTableInstance.kDefaultPort);
                NetworkTableInstance.getDefault().startClient();
            }
            localNetworkTable.stopClient();
        }
    }

    /**
     * This function uses ByteBuddy to redefine each of the hardware JNI classes into
     * our simulator versions. These simulator versions must be EXACTLY the same in structure
     * (no new methods or fields) in order for redefine to work.
     */
    private static void redifineJNIs() {
        Instrumentation agent = ByteBuddyAgent.install();

        redefineClass(HAL.class, SimHALJNI.class);
        redefineClass(SPIJNI.class, SimSPIJNI.class);
        redefineClass(NotifierJNI.class, SimNotifierJNI.class);
        redefineClass(HALUtil.class, SimHALUtilJNI.class);

        // we can't call a simple redefine on the JNIWrapper because
        // it has a nested inner class. No worries, we can disable
        // static load now by just calling setExtractOnStaticLoad false
        JNIWrapper.Helper.setExtractOnStaticLoad(false);

        // the camera server is special, because of the Helper nested class
        redefineCameraServerJNI();

        // we actually want the NetworkTablesJNI so we can write output to Shuffleboard
        // in the simulator and view it
        // redefineClass(NetworkTablesJNI.class, SimNetworkTablesJNI.class);
        redefineClass(ConstantsJNI.class, SimConstantsJNI.class);
        redefineClass(PortsJNI.class, SimPortsJNI.class);

        redefineClass(DIOJNI.class, SimDIOJNI.class);
        redefineClass(PWMJNI.class, SimPWMJNI.class);
        redefineClass(I2CJNI.class, SimI2CJNI.class);
        redefineClass(SolenoidJNI.class, SimSolenoidJNI.class);
        redefineClass(SimDeviceJNI.class, SimSimDeviceJNI.class);
        redefineClass(CompressorJNI.class, SimCompressorJNI.class);

        // CTRE
        if (VendorUtils.isCtreAvailable()) {
            redefineClass(VendorUtils.ctreControllerClass, VendorUtils.simCTREControllerClass);
            redefineClass(VendorUtils.ctreWrapperClass, VendorUtils.simCTREWrapperClass);
        }

        // REV
        if (VendorUtils.isRevAvailable()) {
            redefineClass(VendorUtils.revWrapperClass, VendorUtils.simRevWrapperClass);
            redefineClass(VendorUtils.revControllerClass, VendorUtils.simRevControllerClass);
        }

    }

    /**
     * We don't fully redefine the CameraServerJNI yet, we just
     * force the enumerateSinks function to return an empty array
     * so the robot will run.
     */
    private static void redefineCameraServerJNI() {
        // CameraServerJNI.Helper.setExtractOnStaticLoad(false);
        // redefineClass(CameraServerJNI.class, SimCameraServerJNI.class);

        CameraServerJNI.Helper.setExtractOnStaticLoad(false);
        TypePool typePool = TypePool.Default.ofSystemLoader();
        new ByteBuddy()
                .redefine(typePool.describe("edu.wpi.cscore.CameraServerJNI").resolve(), // do not use 'Bar.class'
                        ClassFileLocator.ForClassLoader.ofSystemLoader())
                .method(named("enumerateSinks")).intercept(FixedValue.value(new int[] {}))
                .make()
                .load(ClassLoader.getSystemClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }

    static void redefineClass(Class clz, Class simClz) {
        new ByteBuddy()
                .with(TypeValidation.DISABLED)
            .redefine(simClz)
            .name(clz.getName())
            .make()
            .load(clz.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }

}
