
package frc.robot.simulator.sim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;

public class SimHALUtil {
    private static final Logger log = LoggerFactory.getLogger(SimHALUtil.class);


    public static short getFPGAVersion() {
        log.warn("getFPGAVersion not implemented yet.");

        return 0;
    }

    public static int getFPGARevision() {
        log.warn("getFPGARevision not implemented yet.");

        return 0;
    }

    public static long getFPGATime() {
        return ChronoUnit.MICROS.between(Instant.EPOCH, Instant.now());
    }

    public static int getHALRuntimeType() {
        return 0;
    }

    public static boolean getFPGAButton() {
        log.warn("getFPGAButton not implemented yet.");

        return false;
    }

    public static String getHALErrorMessage(int code) {
        log.warn("getHALErrorMessage not implemented yet.");

        return null;
    }

    public static int getHALErrno() {
        log.warn("getHALErrno not implemented yet.");

        return 0;
    }

    public static String getHALstrerror(int errno) {
        log.warn("getHALstrerror not implemented yet.");

        return null;
    }

    public static String getHALstrerror() {
        log.warn("getHALstrerror not implemented yet.");

        return null;
    }

}

