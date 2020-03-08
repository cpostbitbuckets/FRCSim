package frc.robot.simulator.sim;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import frc.robot.simulator.network.Client;
import frc.robot.simulator.network.proto.RobotProto;
import frc.robot.simulator.sim.motors.MotorStore;
import frc.robot.simulator.sim.motors.SimMotor;
import frc.robot.simulator.sim.motors.Vendor;
import frc.robot.simulator.sim.utils.ConversionUtils;
import frc.robot.simulator.sim.utils.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimCANSparkMax {
    private static final Logger log = LoggerFactory.getLogger(SimCANSparkMax.class);

    private static final int SUCCESS = 0;
    
    /**
     * This is the client our simulated motor controller will use to communicate with a motor server
     */
    @Inject
    @Named("Client") static Client client;

    @Inject static MotorStore motorStore;

    public static double convertPIDValueToRadians(double value) {
        // p for sparks is in revolutions
        // so if we want 10% power when we are 1 revolution away, the p value is .1
        // we convert this to rads
        return value / (Math.PI *2);
    }

    public static double convertFValueToRadiansPerSecond(double value) {
        // f for sparks is in revolutions per minute
        // in the mentor bot, the spark motors top out at 316 RPM
        // so kF is 1/316, so if 316 RPM is requested, we should respond with 1 (full) output
        // we need to convert this to 1 / radsPerSecond

        if (value == 0) {
            return 0;
        }
        return 1 / ConversionUtils.rpmToRadiansPerSecond(1. / value);
    }

    public static long c_SparkMax_Create(int deviceId, int motortype) {
        SimMotor simMotor = motorStore.createOrUpdateMotor(deviceId, Vendor.Rev);
        client.updateMotor(simMotor.getConfig());

        return simMotor.getHandle();
    }

    public static void c_SparkMax_Destroy(long handle) {
        log.warn("c_SparkMax_Destroy not implemented yet.");

    }

    public static int c_SparkMax_GetFirmwareVersion(long handle) {
        log.warn("c_SparkMax_GetFirmwareVersion not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetDeviceId(long handle) {
        log.warn("c_SparkMax_GetDeviceId not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetMotorType(long handle, int type) {
        log.warn("c_SparkMax_SetMotorType not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetMotorType(long handle) {
        log.warn("c_SparkMax_GetMotorType not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetPeriodicFramePeriod(long handle, int frameId, int periodMs) {
        log.warn("c_SparkMax_SetPeriodicFramePeriod not implemented yet.");

        return 0;
    }

    public static void c_SparkMax_SetControlFramePeriod(long handle, int periodMs) {
        log.warn("c_SparkMax_SetControlFramePeriod not implemented yet.");

    }

    public static int c_SparkMax_GetControlFramePeriod(long handle) {
        log.warn("c_SparkMax_GetControlFramePeriod not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetEncoderPosition(long handle, float position) {
        log.warn("c_SparkMax_SetEncoderPosition not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_RestoreFactoryDefaults(long handle, boolean persist) {
        log.warn("c_SparkMax_RestoreFactoryDefaults not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetFollow(long handle, int followerArbId, int followerCfg) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();
        builder.setFollowingId(followerArbId);
        // update the motor on the server
        RobotProto.MotorConfig updatedMotorConfig = builder.build();
        if (!updatedMotorConfig.equals(motor.getConfig())) {
            motor.setConfig(builder.build());
            client.updateMotor(motor.getConfig());
        }

        return SUCCESS;
    }

    public static float c_SparkMax_SafeFloat(float f) {
        log.warn("c_SparkMax_SafeFloat not implemented yet.");

        return 0;
    }

    public static void c_SparkMax_EnableExternalControl(boolean enable) {
        log.warn("c_SparkMax_EnableExternalControl not implemented yet.");

    }

    public static void c_SparkMax_SetEnable(boolean enable) {
        log.warn("c_SparkMax_SetEnable not implemented yet.");

    }

    public static int c_SparkMax_SetpointCommand(long handle, float value, int ctrlType,
                                                 int pidSlot, float arbFeedforward, int arbFFUnits) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        builder.setCurrentPidProfile(pidSlot);
        RobotProto.MotorConfig.ControlMode controlType = EnumUtils.controlTypeFromInt(ctrlType);

        switch (controlType) {

            case PercentOutput:
                builder.setControlMode(RobotProto.MotorConfig.ControlMode.PercentOutput);
                builder.setTargetOutput(value);
                break;
            case Velocity:
            case MotionVelocity:
                // set the velocity in rads/sec
                builder.setControlMode(RobotProto.MotorConfig.ControlMode.Velocity);
                builder.setTargetVelocity(ConversionUtils.rpmToRadiansPerSecond(value));
                break;
            case Voltage:
                log.warn("c_SparkMax_SetpointCommand.kVoltage not implemented yet.");
                break;
            case Position:
            case MotionPosition:
                // set the position target in radians
                builder.setControlMode(RobotProto.MotorConfig.ControlMode.Position);
                builder.setTargetPosition(ConversionUtils.revolutionsToRadians(value));
                break;
            case Current:
                log.warn("c_SparkMax_SetpointCommand.kCurrent not implemented yet.");
                break;
        }

        // update the motor on the server
        RobotProto.MotorConfig updatedMotorConfig = builder.build();
        if (!updatedMotorConfig.equals(motor.getConfig())) {
            motor.setConfig(builder.build());
            client.updateMotor(motor.getConfig());
        }

        return SUCCESS;
    }

    public static int c_SparkMax_SetInverted(long handle, boolean inverted) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        motor.setConfig(builder.setInverted(inverted).build());
        client.updateMotor(motor.getConfig());
        return 0;
    }

    public static boolean c_SparkMax_GetInverted(long handle) {
        log.warn("c_SparkMax_GetInverted not implemented yet.");

        return false;
    }

    public static int c_SparkMax_SetSmartCurrentLimit(long handle, int stallLimit, int freeLimit, int limitRPM) {
        log.warn("c_SparkMax_SetSmartCurrentLimit not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetSmartCurrentStallLimit(long handle) {
        log.warn("c_SparkMax_GetSmartCurrentStallLimit not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetSmartCurrentFreeLimit(long handle) {
        log.warn("c_SparkMax_GetSmartCurrentFreeLimit not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetSmartCurrentLimitRPM(long handle) {
        log.warn("c_SparkMax_GetSmartCurrentLimitRPM not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetSecondaryCurrentLimit(long handle, float limit, int chopCycles) {
        log.warn("c_SparkMax_SetSecondaryCurrentLimit not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetSecondaryCurrentLimit(long handle) {
        log.warn("c_SparkMax_GetSecondaryCurrentLimit not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetSecondaryCurrentLimitCycles(long handle) {
        log.warn("c_SparkMax_GetSecondaryCurrentLimitCycles not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetIdleMode(long handle, int idlemode) {
        log.warn("c_SparkMax_SetIdleMode not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetIdleMode(long handle) {
        log.warn("c_SparkMax_GetIdleMode not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_EnableVoltageCompensation(long handle, float nominalVoltage) {
        log.warn("c_SparkMax_EnableVoltageCompensation not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetVoltageCompensationNominalVoltage(long handle) {
        log.warn("c_SparkMax_GetVoltageCompensationNominalVoltage not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_DisableVoltageCompensation(long handle) {
        log.warn("c_SparkMax_DisableVoltageCompensation not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetOpenLoopRampRate(long handle, float rate) {
        log.warn("c_SparkMax_SetOpenLoopRampRate not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetOpenLoopRampRate(long handle) {
        log.warn("c_SparkMax_GetOpenLoopRampRate not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetClosedLoopRampRate(long handle, float rate) {
        log.warn("c_SparkMax_SetClosedLoopRampRate not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetClosedLoopRampRate(long handle) {
        log.warn("c_SparkMax_GetClosedLoopRampRate not implemented yet.");

        return 0;
    }

    public static boolean c_SparkMax_IsFollower(long handle) {
        log.warn("c_SparkMax_IsFollower not implemented yet.");

        return false;
    }

    public static int c_SparkMax_GetFaults(long handle) {
        log.warn("c_SparkMax_GetFaults not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetStickyFaults(long handle) {
        log.warn("c_SparkMax_GetStickyFaults not implemented yet.");

        return 0;
    }

    public static boolean c_SparkMax_GetFault(long handle, int faultId) {
        log.warn("c_SparkMax_GetFault not implemented yet.");

        return false;
    }

    public static boolean c_SparkMax_GetStickyFault(long handle, int faultId) {
        log.warn("c_SparkMax_GetStickyFault not implemented yet.");

        return false;
    }

    public static float c_SparkMax_GetBusVoltage(long handle) {
        log.warn("c_SparkMax_GetBusVoltage not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetAppliedOutput(long handle) {
        return (float) motorStore.getOutput(handle).getOutput();
    }

    public static float c_SparkMax_GetOutputCurrent(long handle) {
        return (float) motorStore.getOutput(handle).getOutputCurrent();
    }

    public static float c_SparkMax_GetMotorTemperature(long handle) {
        log.warn("c_SparkMax_GetMotorTemperature not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_ClearFaults(long handle) {
        log.warn("c_SparkMax_ClearFaults not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_BurnFlash(long handle) {
        log.warn("c_SparkMax_BurnFlash not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetCANTimeout(long handle, int timeoutMs) {
        log.warn("c_SparkMax_SetCANTimeout not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_EnableSoftLimit(long handle, int dir, boolean enable) {
        log.warn("c_SparkMax_EnableSoftLimit not implemented yet.");

        return 0;
    }

    public static boolean c_SparkMax_IsSoftLimitEnabled(long handle, int dir) {
        log.warn("c_SparkMax_IsSoftLimitEnabled not implemented yet.");

        return false;
    }

    public static int c_SparkMax_SetSoftLimit(long handle, int dir, float limit) {
        log.warn("c_SparkMax_SetSoftLimit not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetSoftLimit(long handle, int dir) {
        log.warn("c_SparkMax_GetSoftLimit not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetSensorType(long handle, int sensorType) {
//        log.warn("c_SparkMax_SetSensorType not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetLimitPolarity(long handle, int sw, int polarity) {
        log.warn("c_SparkMax_SetLimitPolarity not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetLimitPolarity(long handle, int sw) {
        log.warn("c_SparkMax_GetLimitPolarity not implemented yet.");

        return 0;
    }

    public static boolean c_SparkMax_GetLimitSwitch(long handle, int sw) {
        log.warn("c_SparkMax_GetLimitSwitch not implemented yet.");

        return false;
    }

    public static int c_SparkMax_EnableLimitSwitch(long handle, int sw, boolean enable) {
        log.warn("c_SparkMax_EnableLimitSwitch not implemented yet.");

        return 0;
    }

    public static boolean c_SparkMax_IsLimitEnabled(long handle, int sw) {
        log.warn("c_SparkMax_IsLimitEnabled not implemented yet.");

        return false;
    }

    public static float c_SparkMax_GetAnalogPosition(long handle) {
        log.warn("c_SparkMax_GetAnalogPosition not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetAnalogVelocity(long handle) {
        log.warn("c_SparkMax_GetAnalogVelocity not implemented yet.");



        return 0;
    }

    public static float c_SparkMax_GetAnalogVoltage(long handle) {
        log.warn("c_SparkMax_GetAnalogVoltage not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAnalogPositionConversionFactor(long handle, float conversion) {
        log.warn("c_SparkMax_SetAnalogPositionConversionFactor not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAnalogVelocityConversionFactor(long handle, float conversion) {
        log.warn("c_SparkMax_SetAnalogVelocityConversionFactor not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetAnalogPositionConversionFactor(long handle) {
        log.warn("c_SparkMax_GetAnalogPositionConversionFactor not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetAnalogVelocityConversionFactor(long handle) {
        log.warn("c_SparkMax_GetAnalogVelocityConversionFactor not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAnalogInverted(long handle, boolean inverted) {
        log.warn("c_SparkMax_SetAnalogInverted not implemented yet.");

        return 0;
    }

    public static boolean c_SparkMax_GetAnalogInverted(long handle) {
        log.warn("c_SparkMax_GetAnalogInverted not implemented yet.");

        return false;
    }

    public static int c_SparkMax_SetAnalogAverageDepth(long handle, int depth) {
        log.warn("c_SparkMax_SetAnalogAverageDepth not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetAnalogAverageDepth(long handle) {
        log.warn("c_SparkMax_GetAnalogAverageDepth not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAnalogMeasurementPeriod(long handle, int samples) {
        log.warn("c_SparkMax_SetAnalogMeasurementPeriod not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetAnalogMeasurementPeriod(long handle) {
        log.warn("c_SparkMax_GetAnalogMeasurementPeriod not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAnalogMode(long handle, int mode) {
        log.warn("c_SparkMax_SetAnalogMode not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetAnalogMode(long handle) {
        log.warn("c_SparkMax_GetAnalogMode not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetEncoderPosition(long handle) {
        return (float) ConversionUtils.radiansToRevolutions(motorStore.getOutput(handle).getSensorPosition());
    }

    /**
     * Get the velocity in RPM
     * @param handle
     * @return
     */
    public static float c_SparkMax_GetEncoderVelocity(long handle) {
        return (float) ConversionUtils.perSecondToPerMinute(ConversionUtils.radiansToRevolutions(motorStore.getOutput(handle).getVelocity()));
    }

    public static int c_SparkMax_SetPositionConversionFactor(long handle, float conversion) {
        log.warn("c_SparkMax_SetPositionConversionFactor not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetVelocityConversionFactor(long handle, float conversion) {
        log.warn("c_SparkMax_SetVelocityConversionFactor not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetPositionConversionFactor(long handle) {
        log.warn("c_SparkMax_GetPositionConversionFactor not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetVelocityConversionFactor(long handle) {
        log.warn("c_SparkMax_GetVelocityConversionFactor not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAverageDepth(long handle, int depth) {
        log.warn("c_SparkMax_SetAverageDepth not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetAverageDepth(long handle) {
        log.warn("c_SparkMax_GetAverageDepth not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetMeasurementPeriod(long handle, int samples) {
        log.warn("c_SparkMax_SetMeasurementPeriod not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetMeasurementPeriod(long handle) {
        log.warn("c_SparkMax_GetMeasurementPeriod not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetCountsPerRevolution(long handle, int counts_per_rev) {
        log.warn("c_SparkMax_SetCountsPerRevolution not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetCountsPerRevolution(long handle) {
        log.warn("c_SparkMax_GetCountsPerRevolution not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetCPR(long handle, int cpr) {
        log.warn("c_SparkMax_SetCPR not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetCPR(long handle) {
        log.warn("c_SparkMax_GetCPR not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetEncoderInverted(long handle, boolean inverted) {
        log.warn("c_SparkMax_SetEncoderInverted not implemented yet.");

        return 0;
    }

    public static boolean c_SparkMax_GetEncoderInverted(long handle) {
        log.warn("c_SparkMax_GetEncoderInverted not implemented yet.");

        return false;
    }

    public static int c_SparkMax_SetAltEncoderPosition(long handle, float position) {
        log.warn("c_SparkMax_SetAltEncoderPosition not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetAltEncoderPosition(long handle) {
        log.warn("c_SparkMax_GetAltEncoderPosition not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetAltEncoderVelocity(long handle) {
        log.warn("c_SparkMax_GetAltEncoderVelocity not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAltEncoderPositionFactor(long handle, float conversion) {
        log.warn("c_SparkMax_SetAltEncoderPositionFactor not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAltEncoderVelocityFactor(long handle, float conversion) {
        log.warn("c_SparkMax_SetAltEncoderVelocityFactor not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetAltEncoderPositionFactor(long handle) {
        log.warn("c_SparkMax_GetAltEncoderPositionFactor not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetAltEncoderVelocityFactor(long handle) {
        log.warn("c_SparkMax_GetAltEncoderVelocityFactor not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAltEncoderAverageDepth(long handle, int depth) {
        log.warn("c_SparkMax_SetAltEncoderAverageDepth not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetAltEncoderAverageDepth(long handle) {
        log.warn("c_SparkMax_GetAltEncoderAverageDepth not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAltEncoderMeasurementPeriod(long handle, int samples) {
        log.warn("c_SparkMax_SetAltEncoderMeasurementPeriod not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetAltEncoderMeasurementPeriod(long handle) {
        log.warn("c_SparkMax_GetAltEncoderMeasurementPeriod not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAltEncoderCountsPerRevolution(long handle, int counts_per_rev) {
        log.warn("c_SparkMax_SetAltEncoderCountsPerRevolution not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetAltEncoderCountsPerRevolution(long handle) {
        log.warn("c_SparkMax_GetAltEncoderCountsPerRevolution not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetAltEncoderInverted(long handle, boolean inverted) {
        log.warn("c_SparkMax_SetAltEncoderInverted not implemented yet.");

        return 0;
    }

    public static boolean c_SparkMax_GetAltEncoderInverted(long handle) {
        log.warn("c_SparkMax_GetAltEncoderInverted not implemented yet.");

        return false;
    }

    public static int c_SparkMax_SetDataPortConfig(long handle, int config) {
        log.warn("c_SparkMax_SetDataPortConfig not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetP(long handle, int slotID, float gain) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        // get a builder for this fpid slot
        RobotProto.MotorConfig.FPID.Builder fpidBuilder = builder.getFpidList().get(slotID).toBuilder();

        // update the config value for this fpid slot
        fpidBuilder.setP(convertPIDValueToRadians(gain));
        builder.setFpid(slotID, fpidBuilder);

        motor.setConfig(builder.build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int c_SparkMax_SetI(long handle, int slotID, float gain) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        // get a builder for this fpid slot
        RobotProto.MotorConfig.FPID.Builder fpidBuilder = builder.getFpidList().get(slotID).toBuilder();

        // update the config value for this fpid slot
        fpidBuilder.setI(convertPIDValueToRadians(gain));
        builder.setFpid(slotID, fpidBuilder);

        motor.setConfig(builder.build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int c_SparkMax_SetD(long handle, int slotID, float gain) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        // get a builder for this fpid slot
        RobotProto.MotorConfig.FPID.Builder fpidBuilder = builder.getFpidList().get(slotID).toBuilder();

        // update the config value for this fpid slot
        fpidBuilder.setD(convertPIDValueToRadians(gain));
        builder.setFpid(slotID, fpidBuilder);

        motor.setConfig(builder.build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int c_SparkMax_SetDFilter(long handle, int slotID, float gain) {
        log.warn("c_SparkMax_SetDFilter not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetFF(long handle, int slotID, float gain) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        // get a builder for this fpid slot
        RobotProto.MotorConfig.FPID.Builder fpidBuilder = builder.getFpidList().get(slotID).toBuilder();

        // update the config value for this fpid slot
        fpidBuilder.setF(convertFValueToRadiansPerSecond(gain));
        builder.setFpid(slotID, fpidBuilder);

        motor.setConfig(builder.build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int c_SparkMax_SetIZone(long handle, int slotID, float IZone) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        // get a builder for this fpid slot
        RobotProto.MotorConfig.FPID.Builder fpidBuilder = builder.getFpidList().get(slotID).toBuilder();

        // update the config value for this fpid slot
        fpidBuilder.setIZone(convertPIDValueToRadians(IZone));
        builder.setFpid(slotID, fpidBuilder);

        motor.setConfig(builder.build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int c_SparkMax_SetOutputRange(long handle, int slotID, float min, float max) {
        log.warn("c_SparkMax_SetOutputRange not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetP(long handle, int slotID) {
        log.warn("c_SparkMax_GetP not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetI(long handle, int slotID) {
        log.warn("c_SparkMax_GetI not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetD(long handle, int slotID) {
        log.warn("c_SparkMax_GetD not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetDFilter(long handle, int slotID) {
        log.warn("c_SparkMax_GetDFilter not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetFF(long handle, int slotID) {
        log.warn("c_SparkMax_GetFF not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetIZone(long handle, int slotID) {
        log.warn("c_SparkMax_GetIZone not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetOutputMin(long handle, int slotID) {
        log.warn("c_SparkMax_GetOutputMin not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetOutputMax(long handle, int slotID) {
        log.warn("c_SparkMax_GetOutputMax not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetSmartMotionMaxVelocity(long handle, int slotID, float maxVel) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        motor.setConfig(builder.setMotionMagicCruiseVelocity((int) maxVel).build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int c_SparkMax_SetSmartMotionMaxAccel(long handle, int slotID, float maxAccel) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        motor.setConfig(builder.setMotionMagicAcceleration((int) maxAccel).build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int c_SparkMax_SetSmartMotionMinOutputVelocity(long handle, int slotID, float minVel) {
        log.warn("c_SparkMax_SetSmartMotionMinOutputVelocity not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetSmartMotionAccelStrategy(long handle, int slotID, int accelStrategy) {
        log.warn("c_SparkMax_SetSmartMotionAccelStrategy not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetSmartMotionAllowedClosedLoopError(long handle, int slotID, float allowedError) {
        log.warn("c_SparkMax_SetSmartMotionAllowedClosedLoopError not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetSmartMotionMaxVelocity(long handle, int slotID) {
        log.warn("c_SparkMax_GetSmartMotionMaxVelocity not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetSmartMotionMaxAccel(long handle, int slotID) {
        log.warn("c_SparkMax_GetSmartMotionMaxAccel not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetSmartMotionMinOutputVelocity(long handle, int slotID) {
        log.warn("c_SparkMax_GetSmartMotionMinOutputVelocity not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetSmartMotionAccelStrategy(long handle, int slotID) {
        log.warn("c_SparkMax_GetSmartMotionAccelStrategy not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetSmartMotionAllowedClosedLoopError(long handle, int slotID) {
        log.warn("c_SparkMax_GetSmartMotionAllowedClosedLoopError not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetIMaxAccum(long handle, int slotID, float iMaxAccum) {
        log.warn("c_SparkMax_SetIMaxAccum not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetIMaxAccum(long handle, int slotID) {
        log.warn("c_SparkMax_GetIMaxAccum not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetIAccum(long handle, float iAccum) {
        log.warn("c_SparkMax_SetIAccum not implemented yet.");

        return 0;
    }

    public static float c_SparkMax_GetIAccum(long handle) {
        log.warn("c_SparkMax_GetIAccum not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetFeedbackDevice(long handle, int sensorID) {
        log.warn("c_SparkMax_SetFeedbackDevice not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_SetFeedbackDeviceRange(long handle, float min, float max) {
        log.warn("c_SparkMax_SetFeedbackDeviceRange not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetFeedbackDeviceID(long handle) {
        log.warn("c_SparkMax_GetFeedbackDeviceID not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetAPIMajorRevision() {
        log.warn("c_SparkMax_GetAPIMajorRevision not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetAPIMinorRevision() {
        log.warn("c_SparkMax_GetAPIMinorRevision not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetAPIBuildRevision() {
        log.warn("c_SparkMax_GetAPIBuildRevision not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetAPIVersion() {
        log.warn("c_SparkMax_GetAPIVersion not implemented yet.");

        return 0;
    }

    public static int c_SparkMax_GetLastError(long handle) {
        log.warn("c_SparkMax_GetAPIVersion not implemented yet.");

        return 0;
    }

}
