package frc.robot.simulator.hal.rev;

import com.revrobotics.jni.RevJNIWrapper;
import frc.robot.simulator.sim.SimCANSparkMax;

public class SimCANSparkMaxJNI extends RevJNIWrapper {

    //CANSparkMaxLowLevel
    public static long c_SparkMax_Create(int deviceId, int motortype) {
        return SimCANSparkMax.c_SparkMax_Create(deviceId, motortype);
    }
    public static void c_SparkMax_Destroy(long handle) {
        SimCANSparkMax.c_SparkMax_Destroy(handle);
    }
    public static int c_SparkMax_GetFirmwareVersion(long handle) {
        return SimCANSparkMax.c_SparkMax_GetFirmwareVersion(handle);
    }
    public static int c_SparkMax_GetDeviceId(long handle) {
        return SimCANSparkMax.c_SparkMax_GetDeviceId(handle);
    }
    public static int c_SparkMax_SetMotorType(long handle, int type) {
        return SimCANSparkMax.c_SparkMax_SetMotorType(handle, type);
    }
    public static int c_SparkMax_GetMotorType(long handle) {
        return SimCANSparkMax.c_SparkMax_GetMotorType(handle);
    }
    public static int c_SparkMax_SetPeriodicFramePeriod(long handle, int frameId, int periodMs) {
        return SimCANSparkMax.c_SparkMax_SetPeriodicFramePeriod(handle, frameId, periodMs);
    }

    public static void c_SparkMax_SetControlFramePeriod(long handle, int periodMs) {
        SimCANSparkMax.c_SparkMax_SetControlFramePeriod(handle, periodMs);
    }
    public static int c_SparkMax_GetControlFramePeriod(long handle) {
        return SimCANSparkMax.c_SparkMax_GetControlFramePeriod(handle);
    }

    public static int c_SparkMax_SetEncoderPosition(long handle, float position) {
        return SimCANSparkMax.c_SparkMax_SetEncoderPosition(handle, position);
    }
    public static int c_SparkMax_RestoreFactoryDefaults(long handle, boolean persist) {
        return SimCANSparkMax.c_SparkMax_RestoreFactoryDefaults(handle, persist);
    }

    public static int c_SparkMax_SetFollow(long handle, int followerArbId, int followerCfg) {
        return SimCANSparkMax.c_SparkMax_SetFollow(handle, followerArbId, followerCfg);
    }
    public static float c_SparkMax_SafeFloat(float f) {
        return SimCANSparkMax.c_SparkMax_SafeFloat(f);
    }
    public static void c_SparkMax_EnableExternalControl(boolean enable) {
        SimCANSparkMax.c_SparkMax_EnableExternalControl(enable);
    }
    public static void c_SparkMax_SetEnable(boolean enable) {
        SimCANSparkMax.c_SparkMax_SetEnable(enable);
    }

    public static int c_SparkMax_SetpointCommand(long handle, float value, int ctrlType,
                                                 int pidSlot, float arbFeedforward, int arbFFUnits) {
        return SimCANSparkMax.c_SparkMax_SetpointCommand(handle, value, ctrlType, pidSlot, arbFeedforward, arbFFUnits);
    }

    //CANSparkMax
    public static int c_SparkMax_SetInverted(long handle, boolean inverted) {
        return SimCANSparkMax.c_SparkMax_SetInverted(handle, inverted);
    }
    public static boolean c_SparkMax_GetInverted(long handle) {
        return SimCANSparkMax.c_SparkMax_GetInverted(handle);
    }
    public static int c_SparkMax_SetSmartCurrentLimit(long handle, int stallLimit, int freeLimit, int limitRPM) {
        return SimCANSparkMax.c_SparkMax_SetSmartCurrentLimit(handle, stallLimit, freeLimit, limitRPM);
    }
    public static int c_SparkMax_GetSmartCurrentStallLimit(long handle) {
        return SimCANSparkMax.c_SparkMax_GetSmartCurrentStallLimit(handle);
    }
    public static int c_SparkMax_GetSmartCurrentFreeLimit(long handle) {
        return SimCANSparkMax.c_SparkMax_GetSmartCurrentFreeLimit(handle);
    }
    public static int c_SparkMax_GetSmartCurrentLimitRPM(long handle) {
        return SimCANSparkMax.c_SparkMax_GetSmartCurrentLimitRPM(handle);
    }
    public static int c_SparkMax_SetSecondaryCurrentLimit(long handle, float limit, int chopCycles) {
        return SimCANSparkMax.c_SparkMax_SetSecondaryCurrentLimit(handle, limit, chopCycles);
    }
    public static float c_SparkMax_GetSecondaryCurrentLimit(long handle) {
        return SimCANSparkMax.c_SparkMax_GetSecondaryCurrentLimit(handle);
    }
    public static int c_SparkMax_GetSecondaryCurrentLimitCycles(long handle) {
        return SimCANSparkMax.c_SparkMax_GetSecondaryCurrentLimitCycles(handle);
    }
    public static int c_SparkMax_SetIdleMode(long handle, int idlemode) {
        return SimCANSparkMax.c_SparkMax_SetIdleMode(handle, idlemode);
    }
    public static int c_SparkMax_GetIdleMode(long handle) {
        return SimCANSparkMax.c_SparkMax_GetIdleMode(handle);
    }
    public static int c_SparkMax_EnableVoltageCompensation(long handle, float nominalVoltage) {
        return SimCANSparkMax.c_SparkMax_EnableVoltageCompensation(handle, nominalVoltage);
    }
    public static float c_SparkMax_GetVoltageCompensationNominalVoltage(long handle) {
        return SimCANSparkMax.c_SparkMax_GetVoltageCompensationNominalVoltage(handle);
    }
    public static int c_SparkMax_DisableVoltageCompensation(long handle) {
        return SimCANSparkMax.c_SparkMax_DisableVoltageCompensation(handle);
    }
    public static int c_SparkMax_SetOpenLoopRampRate(long handle, float rate) {
        return SimCANSparkMax.c_SparkMax_SetOpenLoopRampRate(handle, rate);
    }
    public static float c_SparkMax_GetOpenLoopRampRate(long handle) {
        return SimCANSparkMax.c_SparkMax_GetOpenLoopRampRate(handle);
    }
    public static int c_SparkMax_SetClosedLoopRampRate(long handle, float rate) {
        return SimCANSparkMax.c_SparkMax_SetClosedLoopRampRate(handle, rate);
    }
    public static float c_SparkMax_GetClosedLoopRampRate(long handle) {
        return SimCANSparkMax.c_SparkMax_GetClosedLoopRampRate(handle);
    }
    public static boolean c_SparkMax_IsFollower(long handle) {
        return SimCANSparkMax.c_SparkMax_IsFollower(handle);
    }
    public static int c_SparkMax_GetFaults(long handle) {
        return SimCANSparkMax.c_SparkMax_GetFaults(handle);
    }
    public static int c_SparkMax_GetStickyFaults(long handle) {
        return SimCANSparkMax.c_SparkMax_GetStickyFaults(handle);
    }
    public static boolean c_SparkMax_GetFault(long handle, int faultId) {
        return SimCANSparkMax.c_SparkMax_GetFault(handle, faultId);
    }
    public static boolean c_SparkMax_GetStickyFault(long handle, int faultId) {
        return SimCANSparkMax.c_SparkMax_GetStickyFault(handle, faultId);
    }
    public static float c_SparkMax_GetBusVoltage(long handle) {
        return SimCANSparkMax.c_SparkMax_GetBusVoltage(handle);
    }
    public static float c_SparkMax_GetAppliedOutput(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAppliedOutput(handle);
    }
    public static float c_SparkMax_GetOutputCurrent(long handle) {
        return SimCANSparkMax.c_SparkMax_GetOutputCurrent(handle);
    }
    public static float c_SparkMax_GetMotorTemperature(long handle) {
        return SimCANSparkMax.c_SparkMax_GetMotorTemperature(handle);
    }
    public static int c_SparkMax_ClearFaults(long handle) {
        return SimCANSparkMax.c_SparkMax_ClearFaults(handle);
    }
    public static int c_SparkMax_BurnFlash(long handle) {
        return SimCANSparkMax.c_SparkMax_BurnFlash(handle);
    }
    public static int c_SparkMax_SetCANTimeout(long handle, int timeoutMs) {
        return SimCANSparkMax.c_SparkMax_SetCANTimeout(handle, timeoutMs);
    }
    public static int c_SparkMax_EnableSoftLimit(long handle, int dir, boolean enable) {
        return SimCANSparkMax.c_SparkMax_EnableSoftLimit(handle, dir, enable);
    }
    public static boolean c_SparkMax_IsSoftLimitEnabled(long handle, int dir) {
        return SimCANSparkMax.c_SparkMax_IsSoftLimitEnabled(handle, dir);
    }
    public static int c_SparkMax_SetSoftLimit(long handle, int dir, float limit) {
        return SimCANSparkMax.c_SparkMax_SetSoftLimit(handle, dir, limit);
    }
    public static float c_SparkMax_GetSoftLimit(long handle, int dir) {
        return SimCANSparkMax.c_SparkMax_GetSoftLimit(handle, dir);
    }
    public static int c_SparkMax_SetSensorType(long handle, int sensorType) {
        return SimCANSparkMax.c_SparkMax_SetSensorType(handle, sensorType);
    }

    //Digital Input
    public static int c_SparkMax_SetLimitPolarity(long handle, int sw, int polarity) {
        return SimCANSparkMax.c_SparkMax_SetLimitPolarity(handle, sw, polarity);
    }
    public static int c_SparkMax_GetLimitPolarity(long handle, int sw) {
        return SimCANSparkMax.c_SparkMax_GetLimitPolarity(handle, sw);
    }
    public static boolean c_SparkMax_GetLimitSwitch(long handle, int sw) {
        return SimCANSparkMax.c_SparkMax_GetLimitSwitch(handle, sw);
    }
    public static int c_SparkMax_EnableLimitSwitch(long handle, int sw, boolean enable) {
        return SimCANSparkMax.c_SparkMax_EnableLimitSwitch(handle, sw, enable);
    }
    public static boolean c_SparkMax_IsLimitEnabled(long handle, int sw) {
        return SimCANSparkMax.c_SparkMax_IsLimitEnabled(handle, sw);
    }

    //CANAnalog
    public static float c_SparkMax_GetAnalogPosition(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAnalogPosition(handle);
    }
    public static float c_SparkMax_GetAnalogVelocity(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAnalogVelocity(handle);
    }
    public static float c_SparkMax_GetAnalogVoltage(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAnalogVoltage(handle);
    }
    public static int c_SparkMax_SetAnalogPositionConversionFactor(long handle, float conversion) {
        return SimCANSparkMax.c_SparkMax_SetAnalogPositionConversionFactor(handle, conversion);
    }
    public static int c_SparkMax_SetAnalogVelocityConversionFactor(long handle, float conversion) {
        return SimCANSparkMax.c_SparkMax_SetAnalogVelocityConversionFactor(handle, conversion);
    }
    public static float c_SparkMax_GetAnalogPositionConversionFactor(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAnalogPositionConversionFactor(handle);
    }
    public static float c_SparkMax_GetAnalogVelocityConversionFactor(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAnalogVelocityConversionFactor(handle);
    }
    public static int c_SparkMax_SetAnalogInverted(long handle, boolean inverted) {
        return SimCANSparkMax.c_SparkMax_SetAnalogInverted(handle, inverted);
    }
    public static boolean c_SparkMax_GetAnalogInverted(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAnalogInverted(handle);
    }
    public static int c_SparkMax_SetAnalogAverageDepth(long handle, int depth) {
        return SimCANSparkMax.c_SparkMax_SetAnalogAverageDepth(handle, depth);
    }
    public static int c_SparkMax_GetAnalogAverageDepth(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAnalogAverageDepth(handle);
    }
    public static int c_SparkMax_SetAnalogMeasurementPeriod(long handle, int samples) {
        return SimCANSparkMax.c_SparkMax_SetAnalogMeasurementPeriod(handle, samples);
    }
    public static int c_SparkMax_GetAnalogMeasurementPeriod(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAnalogMeasurementPeriod(handle);
    }
    public static int c_SparkMax_SetAnalogMode(long handle, int mode) {
        return SimCANSparkMax.c_SparkMax_SetAnalogMode(handle, mode);
    }
    public static int c_SparkMax_GetAnalogMode(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAnalogMode(handle);
    }

    //CANEncoder
    public static float c_SparkMax_GetEncoderPosition(long handle) {
        return SimCANSparkMax.c_SparkMax_GetEncoderPosition(handle);
    }
    public static float c_SparkMax_GetEncoderVelocity(long handle) {
        return SimCANSparkMax.c_SparkMax_GetEncoderVelocity(handle);
    }
    public static int c_SparkMax_SetPositionConversionFactor(long handle, float conversion) {
        return SimCANSparkMax.c_SparkMax_SetPositionConversionFactor(handle, conversion);
    }
    public static int c_SparkMax_SetVelocityConversionFactor(long handle, float conversion) {
        return SimCANSparkMax.c_SparkMax_SetVelocityConversionFactor(handle, conversion);
    }
    public static float c_SparkMax_GetPositionConversionFactor(long handle) {
        return SimCANSparkMax.c_SparkMax_GetPositionConversionFactor(handle);
    }
    public static float c_SparkMax_GetVelocityConversionFactor(long handle) {
        return SimCANSparkMax.c_SparkMax_GetVelocityConversionFactor(handle);
    }
    public static int c_SparkMax_SetAverageDepth(long handle, int depth) {
        return SimCANSparkMax.c_SparkMax_SetAverageDepth(handle, depth);
    }
    public static int c_SparkMax_GetAverageDepth(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAverageDepth(handle);
    }
    public static int c_SparkMax_SetMeasurementPeriod(long handle, int samples) {
        return SimCANSparkMax.c_SparkMax_SetMeasurementPeriod(handle, samples);
    }
    public static int c_SparkMax_GetMeasurementPeriod(long handle) {
        return SimCANSparkMax.c_SparkMax_GetMeasurementPeriod(handle);
    }
    public static int c_SparkMax_SetCountsPerRevolution(long handle, int counts_per_rev) {
        return SimCANSparkMax.c_SparkMax_SetCountsPerRevolution(handle, counts_per_rev);
    }
    public static int c_SparkMax_GetCountsPerRevolution(long handle) {
        return SimCANSparkMax.c_SparkMax_GetCountsPerRevolution(handle);
    }
    public static int c_SparkMax_SetEncoderInverted(long handle, boolean inverted) {
        return SimCANSparkMax.c_SparkMax_SetEncoderInverted(handle, inverted);
    }
    public static boolean c_SparkMax_GetEncoderInverted(long handle) {
        return SimCANSparkMax.c_SparkMax_GetEncoderInverted(handle);
    }

    // Alternate CANEncoder
    public static int c_SparkMax_SetAltEncoderPosition(long handle, float position) {
        return SimCANSparkMax.c_SparkMax_SetAltEncoderPosition(handle, position);
    }
    public static float c_SparkMax_GetAltEncoderPosition(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAltEncoderPosition(handle);
    }
    public static float c_SparkMax_GetAltEncoderVelocity(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAltEncoderVelocity(handle);
    }
    public static int c_SparkMax_SetAltEncoderPositionFactor(long handle, float conversion) {
        return SimCANSparkMax.c_SparkMax_SetAltEncoderPositionFactor(handle, conversion);
    }
    public static int c_SparkMax_SetAltEncoderVelocityFactor(long handle, float conversion) {
        return SimCANSparkMax.c_SparkMax_SetAltEncoderVelocityFactor(handle, conversion);
    }
    public static float c_SparkMax_GetAltEncoderPositionFactor(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAltEncoderPositionFactor(handle);
    }
    public static float c_SparkMax_GetAltEncoderVelocityFactor(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAltEncoderVelocityFactor(handle);
    }
    public static int c_SparkMax_SetAltEncoderAverageDepth(long handle, int depth) {
        return SimCANSparkMax.c_SparkMax_SetAltEncoderAverageDepth(handle, depth);
    }
    public static int c_SparkMax_GetAltEncoderAverageDepth(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAltEncoderAverageDepth(handle);
    }
    public static int c_SparkMax_SetAltEncoderMeasurementPeriod(long handle, int samples) {
        return SimCANSparkMax.c_SparkMax_SetAltEncoderMeasurementPeriod(handle, samples);
    }
    public static int c_SparkMax_GetAltEncoderMeasurementPeriod(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAltEncoderMeasurementPeriod(handle);
    }
    public static int c_SparkMax_SetAltEncoderCountsPerRevolution(long handle, int counts_per_rev) {
        return SimCANSparkMax.c_SparkMax_SetAltEncoderCountsPerRevolution(handle, counts_per_rev);
    }
    public static int c_SparkMax_GetAltEncoderCountsPerRevolution(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAltEncoderCountsPerRevolution(handle);
    }
    public static int c_SparkMax_SetAltEncoderInverted(long handle, boolean inverted) {
        return SimCANSparkMax.c_SparkMax_SetAltEncoderInverted(handle, inverted);
    }
    public static boolean c_SparkMax_GetAltEncoderInverted(long handle) {
        return SimCANSparkMax.c_SparkMax_GetAltEncoderInverted(handle);
    }
    public static int c_SparkMax_SetDataPortConfig(long handle, int config) {
        return SimCANSparkMax.c_SparkMax_SetDataPortConfig(handle, config);
    }

    //CANPIDController
    public static int c_SparkMax_SetP(long handle, int slotID, float gain) {
        return SimCANSparkMax.c_SparkMax_SetP(handle, slotID, gain);
    }
    public static int c_SparkMax_SetI(long handle, int slotID, float gain) {
        return SimCANSparkMax.c_SparkMax_SetI(handle, slotID, gain);
    }
    public static int c_SparkMax_SetD(long handle, int slotID, float gain) {
        return SimCANSparkMax.c_SparkMax_SetD(handle, slotID, gain);
    }
    public static int c_SparkMax_SetDFilter(long handle, int slotID, float gain) {
        return SimCANSparkMax.c_SparkMax_SetDFilter(handle, slotID, gain);
    }
    public static int c_SparkMax_SetFF(long handle, int slotID, float gain) {
        return SimCANSparkMax.c_SparkMax_SetFF(handle, slotID, gain);
    }
    public static int c_SparkMax_SetIZone(long handle, int slotID, float IZone) {
        return SimCANSparkMax.c_SparkMax_SetIZone(handle, slotID, IZone);
    }
    public static int c_SparkMax_SetOutputRange(long handle, int slotID, float min, float max) {
        return SimCANSparkMax.c_SparkMax_SetOutputRange(handle, slotID, min, max);
    }
    public static float c_SparkMax_GetP(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetP(handle, slotID);
    }
    public static float c_SparkMax_GetI(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetI(handle, slotID);
    }
    public static float c_SparkMax_GetD(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetD(handle, slotID);
    }
    public static float c_SparkMax_GetDFilter(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetDFilter(handle, slotID);
    }
    public static float c_SparkMax_GetFF(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetFF(handle, slotID);
    }
    public static float c_SparkMax_GetIZone(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetIZone(handle, slotID);
    }
    public static float c_SparkMax_GetOutputMin(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetOutputMin(handle, slotID);
    }
    public static float c_SparkMax_GetOutputMax(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetOutputMax(handle, slotID);
    }

    public static int c_SparkMax_SetSmartMotionMaxVelocity(long handle, int slotID, float maxVel) {
        return SimCANSparkMax.c_SparkMax_SetSmartMotionMaxVelocity(handle, slotID, maxVel);
    }
    public static int c_SparkMax_SetSmartMotionMaxAccel(long handle, int slotID, float maxAccel) {
        return SimCANSparkMax.c_SparkMax_SetSmartMotionMaxAccel(handle, slotID, maxAccel);
    }
    public static int c_SparkMax_SetSmartMotionMinOutputVelocity(long handle, int slotID, float minVel) {
        return SimCANSparkMax.c_SparkMax_SetSmartMotionMinOutputVelocity(handle, slotID, minVel);
    }
    public static int c_SparkMax_SetSmartMotionAccelStrategy(long handle, int slotID, int accelStrategy) {
        return SimCANSparkMax.c_SparkMax_SetSmartMotionAccelStrategy(handle, slotID, accelStrategy);
    }
    public static int c_SparkMax_SetSmartMotionAllowedClosedLoopError(long handle, int slotID, float allowedError) {
        return SimCANSparkMax.c_SparkMax_SetSmartMotionAllowedClosedLoopError(handle, slotID, allowedError);
    }
    public static float c_SparkMax_GetSmartMotionMaxVelocity(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetSmartMotionMaxVelocity(handle, slotID);
    }
    public static float c_SparkMax_GetSmartMotionMaxAccel(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetSmartMotionMaxAccel(handle, slotID);
    }
    public static float c_SparkMax_GetSmartMotionMinOutputVelocity(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetSmartMotionMinOutputVelocity(handle, slotID);
    }
    public static int c_SparkMax_GetSmartMotionAccelStrategy(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetSmartMotionAccelStrategy(handle, slotID);
    }
    public static float c_SparkMax_GetSmartMotionAllowedClosedLoopError(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetSmartMotionAllowedClosedLoopError(handle, slotID);
    }

    public static int c_SparkMax_SetIMaxAccum(long handle, int slotID, float iMaxAccum) {
        return SimCANSparkMax.c_SparkMax_SetIMaxAccum(handle, slotID, iMaxAccum);
    }
    public static float c_SparkMax_GetIMaxAccum(long handle, int slotID) {
        return SimCANSparkMax.c_SparkMax_GetIMaxAccum(handle, slotID);
    }
    public static int c_SparkMax_SetIAccum(long handle, float iAccum) {
        return SimCANSparkMax.c_SparkMax_SetIAccum(handle, iAccum);
    }
    public static float c_SparkMax_GetIAccum(long handle) {
        return SimCANSparkMax.c_SparkMax_GetIAccum(handle);
    }

    public static int c_SparkMax_SetFeedbackDevice(long handle, int sensorID) {
        return SimCANSparkMax.c_SparkMax_SetFeedbackDevice(handle, sensorID);
    }
    public static int c_SparkMax_SetFeedbackDeviceRange(long handle, float min, float max) {
        return SimCANSparkMax.c_SparkMax_SetFeedbackDeviceRange(handle, min, max);
    }
    public static int c_SparkMax_GetFeedbackDeviceID(long handle) {
        return SimCANSparkMax.c_SparkMax_GetFeedbackDeviceID(handle);
    }

    public static int c_SparkMax_GetAPIMajorRevision() {
        return SimCANSparkMax.c_SparkMax_GetAPIMajorRevision();
    }
    public static int c_SparkMax_GetAPIMinorRevision() {
        return SimCANSparkMax.c_SparkMax_GetAPIMinorRevision();
    }
    public static int c_SparkMax_GetAPIBuildRevision() {
        return SimCANSparkMax.c_SparkMax_GetAPIBuildRevision();
    }
    public static int c_SparkMax_GetAPIVersion() {
        return SimCANSparkMax.c_SparkMax_GetAPIVersion();
    }

    public static int c_SparkMax_GetLastError(long handle) {
        return SimCANSparkMax.c_SparkMax_GetLastError(handle);
    }
}

