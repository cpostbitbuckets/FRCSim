
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

public class SimMotController {
    private static final Logger log = LoggerFactory.getLogger(SimMotController.class);
    public static final double ctreVoltageReslution = 1023;

    private static final int SUCCESS = 0;

    /**
     * This is the client our simulated motor controller will use to communicate with a motor server
     */
    @Inject
    @Named("Client")
    static Client client;

    @Inject
    static MotorStore motorStore;

    public static double convertPIDValueToRadians(double value, int encoderCountsPerRevolution, RobotProto.MotorConfig.Encoder encoder) {
        // p for talons is something like .1 * 1023 / 8192
        // which means 10% power when we are 1 revolution (8192 ticks) away from our position goal
        // in the above example, the p value we want for our sim is
        // .1 * 1 / (2 * Math.PI) for 10 % power * 1 output / 1 radian, so when we are 1 revolution away
        // we want 10% power

        // first convert this to the output percentage we want for a given number of revolutions
        double outputPerRevolution = value * ConversionUtils.ticksPerRevolution(encoderCountsPerRevolution, encoder) / ctreVoltageReslution;

        // finally, the simulator deals with radians
        return outputPerRevolution / (2 * Math.PI) / 10;
    }

    public static double convertFValueToRadiansPerSecond(double value, int encoderCountsPerRevolution, RobotProto.MotorConfig.Encoder encoder) {
        // f values are in voltage incremements / ticks, so
        // if our motor goes 20,000 ticks at full (1023) voltage, the FPID is
        // 1023 / 20000
        // this way if we want to go 1000 ticks, we give an output of 1000 * (1023/20000)

        // we want this number in power output (-1 to 1) required for revolutions, not ticks
        double outputPerRevolution = value * ConversionUtils.ticksPerRevolution(encoderCountsPerRevolution, encoder) / ctreVoltageReslution;

        // the simulator is expecting rads per second, not revolutions per 100ms
        // so convert this to radians per second
        return outputPerRevolution / (2 * Math.PI) / 10;
    }

    public static void validateEncoder(long handle) {
        SimMotor simMotor = motorStore.get(handle);
        if (simMotor.getConfig().getEncoder() == RobotProto.MotorConfig.Encoder.NotSet) {
            log.warn("Motor " + simMotor.getId() + " has no encoder configured. Call motor.configSelectedFeedbackSensor() to set one up.");
        }
    }

    /**
     * Create a new motor based on some arbitrary id (for talons, this is our handle)
     *
     * @param baseArbId
     * @return
     */
    public static long Create(int baseArbId) {
        SimMotor simMotor = motorStore.createOrUpdateMotor(baseArbId, Vendor.CTRE);
        client.updateMotor(simMotor.getConfig());
        return simMotor.getHandle();
    }

    public static long Create2(int deviceID, String model) {
        SimMotor simMotor = motorStore.createOrUpdateMotor(deviceID, Vendor.CTRE);
        // TODO: set motor
        client.updateMotor(simMotor.getConfig());
        return simMotor.getHandle();
    }

    public static int JNI_destroy_MotController(long handle) {
        log.warn("JNI_destroy_MotController not implemented yet.");
        return 0;
    }

    public static int GetDeviceNumber(long handle) {
        // this is actually just the handle
        return motorStore.get(handle).getId();
    }

    public static int GetBaseID(long handle) {
        // this is actually just the handle
        return motorStore.get(handle).getId();
    }

    public static void SetDemand(long handle, int mode, int demand0, int demand1) {
        SimMotor simMotor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = simMotor.getConfig().toBuilder();

        RobotProto.MotorConfig.ControlMode controlMode = EnumUtils.controlModeFromInt(mode);
        switch (controlMode) {

            case PercentOutput:
                log.warn("SetDemand not implemented yet.");
                break;
            case Position:
                log.warn("SetDemand not implemented yet.");
                break;
            case Velocity:
                log.warn("SetDemand not implemented yet.");
                break;
            case Current:
                log.warn("SetDemand not implemented yet.");
                break;
            case Follower:
                log.warn("SetDemand not implemented yet.");
                break;
            case MotionProfile:
                log.warn("SetDemand not implemented yet.");
                break;
            case MotionPosition:
                log.warn("SetDemand not implemented yet.");
                break;
            case MotionProfileArc:
                log.warn("SetDemand not implemented yet.");
                break;
            case Disabled:
                // 0 the motor output
                builder.setControlMode(RobotProto.MotorConfig.ControlMode.Disabled);
                builder.setTargetOutput(0);
                break;
        }

        RobotProto.MotorConfig updatedMotorConfig = builder.build();
        if (!updatedMotorConfig.equals(simMotor.getConfig())) {
            simMotor.setConfig(builder.build());
            client.updateMotor(simMotor.getConfig());
        }
    }

    public static void Set_4(long handle, int mode, double demand0, double demand1, int demand1Type) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        RobotProto.MotorConfig.ControlMode controlMode = EnumUtils.controlModeFromInt(mode);

        int ticksPerRevolution = ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder());

//        log.info("Set_4: " + controlMode + " demand0: " + demand0);
        switch (controlMode) {

            case PercentOutput:
                builder.setControlMode(RobotProto.MotorConfig.ControlMode.PercentOutput);
                builder.setTargetOutput((float) demand0);
                break;
            case Position:
                // these both set position, but they compute the PID differently
                builder.setControlMode(RobotProto.MotorConfig.ControlMode.Position)
                        .setTargetPosition(ConversionUtils.ticksToRadians((int) demand0, ticksPerRevolution));
                break;
            case MotionPosition:
                // these both set position, but they compute the PID differently
                builder.setControlMode(RobotProto.MotorConfig.ControlMode.MotionPosition)
                        .setTargetPosition(ConversionUtils.ticksToRadians((int) demand0, ticksPerRevolution));
                break;
            case Velocity:
                // set the velocity in radians per second
                builder.setControlMode(RobotProto.MotorConfig.ControlMode.Velocity);
                builder.setTargetVelocity(ConversionUtils.ticksPer100msToRadiansPerSecond((int) demand0, ticksPerRevolution));
                break;
            case Current:
                log.warn("Set_4.Current not implemented yet.");
                break;
            case Follower:
                // Bitwise man, ugh, why??
                builder.setControlMode(RobotProto.MotorConfig.ControlMode.Follower);
                int followerId = (int) demand0 & 0xFF;
                builder.setFollowingId(followerId);
                break;
            case MotionProfile:
                log.warn("Set_4.MotionProfile not implemented yet.");
                break;
            case MotionProfileArc:
                log.warn("Set_4.MotionProfileArc not implemented yet.");
                break;
            case Disabled:
                log.warn("Set_4.Disabled not implemented yet.");
                break;
        }

        // update the motor on the server
        RobotProto.MotorConfig updatedMotorConfig = builder.build();
        if (!updatedMotorConfig.equals(motor.getConfig())) {
            motor.setConfig(builder.build());
            client.updateMotor(motor.getConfig());
        }
    }

    public static void SetNeutralMode(long handle, int neutralMode) {
        log.warn("SetNeutralMode not implemented yet.");
    }

    public static void SetSensorPhase(long handle, boolean sensorPhase) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        motor.setConfig(builder.setSensorPhase(sensorPhase).build());
        client.updateMotor(motor.getConfig());
    }

    public static void SetInverted(long handle, boolean invert) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        motor.setConfig(builder.setInverted(invert).build());
        client.updateMotor(motor.getConfig());
    }

    public static void SetInverted_2(long handle, int invert) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        motor.setConfig(builder.setInverted(invert == 1).build());
        client.updateMotor(motor.getConfig());
    }

    public static int ConfigFactoryDefault(long handle, int timeoutMs) {
        log.warn("ConfigFactoryDefault not implemented yet.");

        return 0;
    }

    public static int ConfigOpenLoopRamp(long handle, double secondsFromNeutralToFull, int timeoutMs) {
        log.warn("ConfigOpenLoopRamp not implemented yet.");

        return 0;
    }

    public static int ConfigClosedLoopRamp(long handle, double secondsFromNeutralToFull, int timeoutMs) {
        log.warn("ConfigClosedLoopRamp not implemented yet.");

        return 0;
    }

    public static int ConfigPeakOutputForward(long handle, double percentOut, int timeoutMs) {
        log.warn("ConfigPeakOutputForward not implemented yet.");

        return 0;
    }

    public static int ConfigPeakOutputReverse(long handle, double percentOut, int timeoutMs) {
        log.warn("ConfigPeakOutputReverse not implemented yet.");

        return 0;
    }

    public static int ConfigNominalOutputForward(long handle, double percentOut, int timeoutMs) {
        log.warn("ConfigNominalOutputForward not implemented yet.");

        return 0;
    }

    public static int ConfigNominalOutputReverse(long handle, double percentOut, int timeoutMs) {
        log.warn("ConfigNominalOutputReverse not implemented yet.");

        return 0;
    }

    public static int ConfigNeutralDeadband(long handle, double percentDeadband, int timeoutMs) {
        log.warn("ConfigNeutralDeadband not implemented yet.");

        return 0;
    }

    public static int ConfigVoltageCompSaturation(long handle, double voltage, int timeoutMs) {
        log.warn("ConfigVoltageCompSaturation not implemented yet.");

        return 0;
    }

    public static int ConfigVoltageMeasurementFilter(long handle, int filterWindowSamples, int timeoutMs) {
        log.warn("ConfigVoltageMeasurementFilter not implemented yet.");

        return 0;
    }

    public static void EnableVoltageCompensation(long handle, boolean enable) {
        log.warn("EnableVoltageCompensation not implemented yet.");

    }

    public static boolean GetInverted(long handle) {
        return motorStore.get(handle).getConfig().getInverted();
    }

    public static double GetBusVoltage(long handle) {
        log.warn("GetBusVoltage not implemented yet.");

        return 0;
    }

    public static double GetMotorOutputPercent(long handle) {
        return motorStore.getOutput(handle).getOutput();
    }

    public static double GetOutputCurrent(long handle) {
        return motorStore.getOutput(handle).getOutputCurrent();
    }

    public static double GetSupplyCurrent(long handle) {
        log.warn("GetSupplyCurrent not implemented yet.");

        return 0;
    }

    public static double GetStatorCurrent(long handle) {
        log.warn("GetStatorCurrent not implemented yet.");

        return 0;
    }

    public static double GetTemperature(long handle) {
//        log.warn("GetTemperature not implemented yet.");

        return 0;
    }

    public static int ConfigRemoteFeedbackFilter(long handle, int deviceID, int remoteSensorSource,
                                                 int remoteOrdinal, int timeoutMs) {
        log.warn("ConfigRemoteFeedbackFilter not implemented yet.");

        return 0;
    }

    public static int ConfigSelectedFeedbackSensor(long handle, int feedbackDevice, int pidIdx, int timeoutMs) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motorStore.get(handle).getConfig().toBuilder();
        RobotProto.MotorConfig config = builder.setEncoder(RobotProto.MotorConfig.Encoder.forNumber(feedbackDevice)).build();
        motor.setConfig(config);
        client.updateMotor(config);
        return SUCCESS;
    }

    public static int ConfigSensorTerm(long handle, int sensorTerm, int feedbackDevice, int timeoutMs) {
        log.warn("ConfigSensorTerm not implemented yet.");

        return 0;
    }

    public static int GetSelectedSensorPosition(long handle, int pidIdx) {
        validateEncoder(handle);
        SimMotor motor = motorStore.get(handle);
        return ConversionUtils.radiansToTicks(motorStore.getOutput(handle).getSensorPosition(),
                ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder()));
    }

    public static int GetSelectedSensorVelocity(long handle, int pidIdx) {
        validateEncoder(handle);
        SimMotor motor = motorStore.get(handle);
        int ticksPerRevolution = ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder());
        return ConversionUtils.radiansPerSecondToTicksPer100ms(motorStore.getOutput(handle).getVelocity(), ticksPerRevolution);
    }

    public static int SetSelectedSensorPosition(long handle, int sensorPos, int pidIdx, int timeoutMs) {
        validateEncoder(handle);
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();
        int ticksPerRevolution = ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder());
        double updatedSensorPosition = ConversionUtils.ticksToRadians(sensorPos, ticksPerRevolution);
        RobotProto.MotorConfig config = builder.setSelectedSensorPosition(updatedSensorPosition).build();
        motor.setSensorPosition(updatedSensorPosition);
        motor.setConfig(config);
        client.updateMotor(config);
        return SUCCESS;
    }

    public static int SetControlFramePeriod(long handle, int frame, int periodMs) {
        log.warn("SetControlFramePeriod not implemented yet.");

        return 0;
    }

    public static int SetStatusFramePeriod(long handle, int frame, int periodMs, int timeoutMs) {
        // do nothing on these, we ignore them in the simulator
        return SUCCESS;
    }

    public static int GetStatusFramePeriod(long handle, int frame, int timeoutMs) {
        log.warn("GetStatusFramePeriod not implemented yet.");

        return 0;
    }

    public static int ConfigVelocityMeasurementPeriod(long handle, int period, int timeoutMs) {
        log.warn("ConfigVelocityMeasurementPeriod not implemented yet.");

        return 0;
    }

    public static int ConfigVelocityMeasurementWindow(long handle, int windowSize, int timeoutMs) {
        log.warn("ConfigVelocityMeasurementWindow not implemented yet.");

        return 0;
    }

    public static int ConfigForwardLimitSwitchSource(long handle, int type, int normalOpenOrClose, int deviceID,
                                                     int timeoutMs) {
        log.warn("ConfigForwardLimitSwitchSource not implemented yet.");

        return 0;
    }

    public static int ConfigReverseLimitSwitchSource(long handle, int type, int normalOpenOrClose, int deviceID,
                                                     int timeoutMs) {
        log.warn("ConfigReverseLimitSwitchSource not implemented yet.");

        return 0;
    }

    public static void OverrideLimitSwitchesEnable(long handle, boolean enable) {
        log.warn("OverrideLimitSwitchesEnable not implemented yet.");

    }

    public static int ConfigForwardSoftLimitThreshold(long handle, int forwardSensorLimit, int timeoutMs) {
        log.warn("ConfigForwardSoftLimitThreshold not implemented yet.");

        return 0;
    }

    public static int ConfigReverseSoftLimitThreshold(long handle, int reverseSensorLimit, int timeoutMs) {
        log.warn("ConfigReverseSoftLimitThreshold not implemented yet.");

        return 0;
    }

    public static int ConfigForwardSoftLimitEnable(long handle, boolean enable, int timeoutMs) {
        log.warn("ConfigForwardSoftLimitEnable not implemented yet.");

        return 0;
    }

    public static int ConfigReverseSoftLimitEnable(long handle, boolean enable, int timeoutMs) {
        log.warn("ConfigReverseSoftLimitEnable not implemented yet.");

        return 0;
    }

    public static void OverrideSoftLimitsEnable(long handle, boolean enable) {
        log.warn("OverrideSoftLimitsEnable not implemented yet.");

    }

    public static int Config_kP(long handle, int slotIdx, double value, int timeoutMs) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        // get a builder for this fpid slot
        RobotProto.MotorConfig.FPID.Builder fpidBuilder = builder.getFpidList().get(slotIdx).toBuilder();

        // update the config value for this fpid slot
        fpidBuilder.setP(convertPIDValueToRadians(value, motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder()));
        builder.setFpid(slotIdx, fpidBuilder);


        motor.setConfig(builder.build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int Config_kI(long handle, int slotIdx, double value, int timeoutMs) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        // get a builder for this fpid slot
        RobotProto.MotorConfig.FPID.Builder fpidBuilder = builder.getFpidList().get(slotIdx).toBuilder();

        // update the config value for this fpid slot
        fpidBuilder.setI(convertPIDValueToRadians(value, motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder()));
        builder.setFpid(slotIdx, fpidBuilder);

        motor.setConfig(builder.build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int Config_kD(long handle, int slotIdx, double value, int timeoutMs) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        // get a builder for this fpid slot
        RobotProto.MotorConfig.FPID.Builder fpidBuilder = builder.getFpidList().get(slotIdx).toBuilder();

        // update the config value for this fpid slot
        fpidBuilder.setD(convertPIDValueToRadians(value, motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder()));
        builder.setFpid(slotIdx, fpidBuilder);

        motor.setConfig(builder.build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int Config_kF(long handle, int slotIdx, double value, int timeoutMs) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        // get a builder for this fpid slot
        RobotProto.MotorConfig.FPID.Builder fpidBuilder = builder.getFpidList().get(slotIdx).toBuilder();

        fpidBuilder.setF(convertFValueToRadiansPerSecond(value, motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder()));
        builder.setFpid(slotIdx, fpidBuilder);

        motor.setConfig(builder.build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int Config_IntegralZone(long handle, int slotIdx, double izone, int timeoutMs) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        // get a builder for this fpid slot
        RobotProto.MotorConfig.FPID.Builder fpidBuilder = builder.getFpidList().get(slotIdx).toBuilder();

        // update the config value for this fpid slot
        // izone is in ticks, we wnat radians
        int ticksPerRevolution = ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder());
        fpidBuilder.setIZone(ConversionUtils.ticksToRadians((int) izone, ticksPerRevolution));
        builder.setFpid(slotIdx, fpidBuilder);

        motor.setConfig(builder.build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int ConfigAllowableClosedloopError(long handle, int slotIdx, int allowableClosedLoopError,
                                                     int timeoutMs) {
        log.warn("ConfigAllowableClosedloopError not implemented yet.");

        return 0;
    }

    public static int ConfigMaxIntegralAccumulator(long handle, int slotIdx, double iaccum, int timeoutMs) {
        log.warn("ConfigMaxIntegralAccumulator not implemented yet.");

        return 0;
    }

    public static int SetIntegralAccumulator(long handle, double iaccum, int pidIdx, int timeoutMs) {
        log.warn("SetIntegralAccumulator not implemented yet.");

        return 0;
    }

    public static int GetClosedLoopError(long handle, int pidIdx) {
        SimMotor motor = motorStore.get(handle);
        return ConversionUtils.radiansToTicks(motorStore.getOutput(handle).getLastError(),
                ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder()));
    }

    public static double GetIntegralAccumulator(long handle, int pidIdx) {
        log.warn("GetIntegralAccumulator not implemented yet.");

        return 0;
    }

    public static double GetErrorDerivative(long handle, int pidIdx) {
        log.warn("GetErrorDerivative not implemented yet.");

        return 0;
    }

    public static void SelectProfileSlot(long handle, int slotIdx, int pidIdx) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        motor.setConfig(builder.setCurrentPidProfile(slotIdx).build());
        client.updateMotor(motor.getConfig());
    }

    public static int GetActiveTrajectoryPosition(long handle) {
        log.warn("GetActiveTrajectoryPosition not implemented yet.");

        return 0;
    }

    public static int GetActiveTrajectoryVelocity(long handle) {
        log.warn("GetActiveTrajectoryVelocity not implemented yet.");

        return 0;
    }

    public static double GetActiveTrajectoryHeading(long handle) {
        log.warn("GetActiveTrajectoryHeading not implemented yet.");

        return 0;
    }

    public static int GetActiveTrajectoryPosition3(long handle, int pidIdx) {
        log.warn("GetActiveTrajectoryPosition3 not implemented yet.");

        return 0;
    }

    public static int GetActiveTrajectoryVelocity3(long handle, int pidIdx) {
        log.warn("GetActiveTrajectoryVelocity3 not implemented yet.");

        return 0;
    }

    public static double GetActiveTrajectoryArbFeedFwd3(long handle, int pidIdx) {
        log.warn("GetActiveTrajectoryArbFeedFwd3 not implemented yet.");

        return 0;
    }

    public static int ConfigMotionCruiseVelocity(long handle, int sensorUnitsPer100ms, int timeoutMs) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        int ticksPerRevolution = ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder());
        motor.setConfig(builder.setMotionMagicCruiseVelocity(ConversionUtils.ticksToRadians(sensorUnitsPer100ms, ticksPerRevolution)).build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int ConfigMotionAcceleration(long handle, int sensorUnitsPer100msPerSec, int timeoutMs) {
        SimMotor motor = motorStore.get(handle);
        RobotProto.MotorConfig.Builder builder = motor.getConfig().toBuilder();

        int ticksPerRevolution = ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder());
        motor.setConfig(builder.setMotionMagicAcceleration(ConversionUtils.ticksToRadians(sensorUnitsPer100msPerSec, ticksPerRevolution)).build());
        client.updateMotor(motor.getConfig());
        return SUCCESS;
    }

    public static int ConfigMotionSCurveStrength(long m_handle, int curveStrength, int timeoutMs) {
        log.warn("ConfigMotionSCurveStrength not implemented yet.");

        return 0;
    }

    public static int ClearMotionProfileTrajectories(long handle) {
        log.warn("ClearMotionProfileTrajectories not implemented yet.");

        return 0;
    }

    public static int GetMotionProfileTopLevelBufferCount(long handle) {
        log.warn("GetMotionProfileTopLevelBufferCount not implemented yet.");

        return 0;
    }

    public static int PushMotionProfileTrajectory(long handle, double position, double velocity,
                                                  double headingDeg, int profileSlotSelect, boolean isLastPoint, boolean zeroPos) {
        log.warn("PushMotionProfileTrajectory not implemented yet.");

        return 0;
    }

    public static int PushMotionProfileTrajectory2(long handle, double position, double velocity, double headingDeg,
                                                   int profileSlotSelect0, int profileSlotSelect1, boolean isLastPoint, boolean zeroPos, int durationMs) {
        log.warn("PushMotionProfileTrajectory2 not implemented yet.");

        return 0;
    }

    public static int PushMotionProfileTrajectory3(long handle, double position, double velocity, double arbFeedFwd, double auxiliaryPos, double auxiliaryVel, double auxiliaryArbFeedFwd, int profileSlotSelect0, int profileSlotSelect1, boolean isLastPoint, boolean zeroPos0, int timeDur, boolean useAuxPID) {
        log.warn("PushMotionProfileTrajectory3 not implemented yet.");

        return 0;
    }

    public static int StartMotionProfile(long handle, long streamHandle, int minBufferedPts, int controlMode) {
        log.warn("StartMotionProfile not implemented yet.");

        return 0;
    }

    public static boolean IsMotionProfileTopLevelBufferFull(long handle) {
        log.warn("IsMotionProfileTopLevelBufferFull not implemented yet.");

        return false;
    }

    public static boolean IsMotionProfileFinished(long handle) {
        log.warn("IsMotionProfileFinished not implemented yet.");

        return false;
    }

    public static int ProcessMotionProfileBuffer(long handle) {
        log.warn("ProcessMotionProfileBuffer not implemented yet.");

        return 0;
    }

    public static int GetMotionProfileStatus(long handle, int[] toFill_9) {
        log.warn("GetMotionProfileStatus not implemented yet.");

        return 0;
    }

    public static int GetMotionProfileStatus2(long handle, int[] toFill_11) {
        log.warn("GetMotionProfileStatus2 not implemented yet.");

        return 0;
    }

    public static int ClearMotionProfileHasUnderrun(long handle, int timeoutMs) {
        log.warn("ClearMotionProfileHasUnderrun not implemented yet.");

        return 0;
    }

    public static int ChangeMotionControlFramePeriod(long handle, int periodMs) {
        log.warn("ChangeMotionControlFramePeriod not implemented yet.");

        return 0;
    }

    public static int ConfigMotionProfileTrajectoryPeriod(long handle, int periodMs, int timeoutMs) {
        log.warn("ConfigMotionProfileTrajectoryPeriod not implemented yet.");

        return 0;
    }

    public static int ConfigMotionProfileTrajectoryInterpolationEnable(long handle, boolean enable, int timeoutMs) {
        log.warn("ConfigMotionProfileTrajectoryInterpolationEnable not implemented yet.");

        return 0;
    }

    public static int ConfigFeedbackNotContinuous(long handle, boolean feedbackNotContinuous, int timeoutMs) {
        log.warn("ConfigFeedbackNotContinuous not implemented yet.");

        return 0;
    }

    public static int ConfigRemoteSensorClosedLoopDisableNeutralOnLOS(long handle, boolean remoteSensorClosedLoopDisableNeutralOnLOS, int timeoutMs) {
        log.warn("ConfigRemoteSensorClosedLoopDisableNeutralOnLOS not implemented yet.");

        return 0;
    }

    public static int ConfigClearPositionOnLimitF(long handle, boolean clearPositionOnLimitF, int timeoutMs) {
        log.warn("ConfigClearPositionOnLimitF not implemented yet.");

        return 0;
    }

    public static int ConfigClearPositionOnLimitR(long handle, boolean clearPositionOnLimitR, int timeoutMs) {
        log.warn("ConfigClearPositionOnLimitR not implemented yet.");

        return 0;
    }

    public static int ConfigClearPositionOnQuadIdx(long handle, boolean clearPositionOnQuadIdx, int timeoutMs) {
        log.warn("ConfigClearPositionOnQuadIdx not implemented yet.");

        return 0;
    }

    public static int ConfigLimitSwitchDisableNeutralOnLOS(long handle, boolean limitSwitchDisableNeutralOnLOS, int timeoutMs) {
        log.warn("ConfigLimitSwitchDisableNeutralOnLOS not implemented yet.");

        return 0;
    }

    public static int ConfigSoftLimitDisableNeutralOnLOS(long handle, boolean softLimitDisableNeutralOnLOS, int timeoutMs) {
        log.warn("ConfigSoftLimitDisableNeutralOnLOS not implemented yet.");

        return 0;
    }

    public static int ConfigPulseWidthPeriod_EdgesPerRot(long handle, int pulseWidthPeriod_EdgesPerRot, int timeoutMs) {
        log.warn("ConfigPulseWidthPeriod_EdgesPerRot not implemented yet.");

        return 0;
    }

    public static int ConfigPulseWidthPeriod_FilterWindowSz(long handle, int pulseWidthPeriod_FilterWindowSz, int timeoutMs) {
        log.warn("ConfigPulseWidthPeriod_FilterWindowSz not implemented yet.");

        return 0;
    }

    public static int GetLastError(long handle) {
        SimMotor motor = motorStore.get(handle);
        return ConversionUtils.radiansToTicks(motorStore.getOutput(handle).getLastError(),
                ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder()));
    }

    public static int GetFirmwareVersion(long handle) {
        log.warn("GetFirmwareVersion not implemented yet.");

        return 0;
    }

    public static boolean HasResetOccurred(long handle) {
        log.warn("HasResetOccurred not implemented yet.");

        return false;
    }

    public static int ConfigSetCustomParam(long handle, int newValue, int paramIndex, int timeoutMs) {
        log.warn("ConfigSetCustomParam not implemented yet.");

        return 0;
    }

    public static int ConfigGetCustomParam(long handle, int paramIndex, int timoutMs) {
        log.warn("ConfigGetCustomParam not implemented yet.");

        return 0;
    }

    public static int ConfigSetParameter(long handle, int param, double value, int subValue, int ordinal,
                                         int timeoutMs) {
        log.warn("ConfigSetParameter not implemented yet.");

        return 0;
    }

    public static double ConfigGetParameter(long handle, int param, int ordinal, int timeoutMs) {
        log.warn("ConfigGetParameter not implemented yet.");

        return 0;
    }

    public static int ConfigPeakCurrentLimit(long handle, int amps, int timeoutMs) {
        log.warn("ConfigPeakCurrentLimit not implemented yet.");

        return 0;
    }

    public static int ConfigPeakCurrentDuration(long handle, int milliseconds, int timeoutMs) {
        log.warn("ConfigPeakCurrentDuration not implemented yet.");

        return 0;
    }

    public static int ConfigContinuousCurrentLimit(long handle, int amps, int timeoutMs) {
        log.warn("ConfigContinuousCurrentLimit not implemented yet.");

        return 0;
    }

    public static int EnableCurrentLimit(long handle, boolean enable) {
        log.warn("EnableCurrentLimit not implemented yet.");

        return 0;
    }

    public static int GetAnalogIn(long handle) {
        log.warn("GetAnalogIn not implemented yet.");

        return 0;
    }

    public static int SetAnalogPosition(long handle, int newPosition, int timeoutMs) {
        log.warn("SetAnalogPosition not implemented yet.");

        return 0;
    }

    public static int GetAnalogInRaw(long handle) {
        log.warn("GetAnalogInRaw not implemented yet.");

        return 0;
    }

    public static int GetAnalogInVel(long handle) {
        log.warn("GetAnalogInVel not implemented yet.");

        return 0;
    }

    public static int GetQuadraturePosition(long handle) {
        log.warn("GetQuadraturePosition not implemented yet.");

        return 0;
    }

    public static int SetQuadraturePosition(long handle, int newPosition, int timeoutMs) {
        log.warn("SetQuadraturePosition not implemented yet.");
        return 0;
    }

    public static int GetQuadratureVelocity(long handle) {
        log.warn("GetQuadratureVelocity not implemented yet.");

        return 0;
    }

    public static int GetPulseWidthPosition(long handle) {
        log.warn("GetPulseWidthPosition not implemented yet.");

        return 0;
    }

    public static int SetPulseWidthPosition(long handle, int newPosition, int timeoutMs) {
        log.warn("SetPulseWidthPosition not implemented yet.");

        return 0;
    }

    public static int GetPulseWidthVelocity(long handle) {
        log.warn("GetPulseWidthVelocity not implemented yet.");

        return 0;
    }

    public static int GetPulseWidthRiseToFallUs(long handle) {
        log.warn("GetPulseWidthRiseToFallUs not implemented yet.");

        return 0;
    }

    public static int GetPulseWidthRiseToRiseUs(long handle) {
        log.warn("GetPulseWidthRiseToRiseUs not implemented yet.");

        return 0;
    }

    public static int GetPinStateQuadA(long handle) {
        log.warn("GetPinStateQuadA not implemented yet.");

        return 0;
    }

    public static int GetPinStateQuadB(long handle) {
        log.warn("GetPinStateQuadB not implemented yet.");

        return 0;
    }

    public static int GetPinStateQuadIdx(long handle) {
        log.warn("GetPinStateQuadIdx not implemented yet.");

        return 0;
    }

    public static int IsFwdLimitSwitchClosed(long handle) {
        // we don't support limit switches, but assume they are never closed
        return 0;
    }

    public static int IsRevLimitSwitchClosed(long handle) {
        // we don't support limit switches, but assume they are never closed
        return 0;
    }

    public static int GetFaults(long handle) {
        log.warn("GetFaults not implemented yet.");

        return 0;
    }

    public static int GetStickyFaults(long handle) {
        log.warn("GetStickyFaults not implemented yet.");

        return 0;
    }

    public static int ClearStickyFaults(long handle, int timeoutMs) {
        log.warn("ClearStickyFaults not implemented yet.");

        return 0;
    }

    public static int SelectDemandType(long handle, int enable) {
        log.warn("SelectDemandType not implemented yet.");

        return 0;
    }

    public static int SetMPEOutput(long handle, int mpeOutput) {
        log.warn("SetMPEOutput not implemented yet.");

        return 0;
    }

    public static int EnableHeadingHold(long handle, int enable) {
        log.warn("EnableHeadingHold not implemented yet.");

        return 0;
    }

    public static int GetClosedLoopTarget(long handle, int pidIdx) {
        SimMotor motor = motorStore.get(handle);
        int ticksPerRevolution = ConversionUtils.ticksPerRevolution(motor.getConfig().getEncoderCountsPerRevolution(), motor.getConfig().getEncoder());
        switch (motor.getConfig().getControlMode()) {
            case PercentOutput:
                // ignore this, but don't log it
                return 0;
            case Velocity:
            case MotionVelocity:
                return ConversionUtils.radiansPerSecondToTicksPer100ms(motor.getConfig().getTargetVelocity(), ticksPerRevolution);
            case Position:
            case MotionPosition:
                return ConversionUtils.radiansToTicks(motor.getConfig().getTargetPosition(), ticksPerRevolution);
            default:
                log.warn("GetClosedLoopTarget for ControlMode " + motor.getConfig().getControlMode().toString() + " not implemented yet.");
                break;
        }

        return 0;
    }

    public static int ConfigSelectedFeedbackCoefficient(long handle, double coefficient, int pidIdx, int timeoutMs) {
        log.warn("ConfigSelectedFeedbackCoefficient not implemented yet.");

        return 0;
    }

    public static int ConfigClosedLoopPeakOutput(long handle, int slotIdx, double percentOut, int timeoutMs) {
        log.warn("ConfigClosedLoopPeakOutput not implemented yet.");

        return 0;
    }

    public static int ConfigClosedLoopPeriod(long handle, int slotIdx, int loopTimeMs, int timeoutMs) {
        log.warn("ConfigClosedLoopPeriod not implemented yet.");

        return 0;
    }

    public static int ConfigMotorCommutation(long handle, int motorCommutation, int timeoutMs) {
        log.warn("ConfigMotorCommutation not implemented yet.");

        return 0;
    }

    public static int ConfigGetMotorCommutation(long handle, int timeoutMs) {
        log.warn("ConfigGetMotorCommutation not implemented yet.");

        return 0;
    }

    public static int ConfigSupplyCurrentLimit(long handle, double[] params, int timeoutMs) {
        log.warn("ConfigSupplyCurrentLimit not implemented yet.");

        return 0;
    }

    public static int ConfigStatorCurrentLimit(long handle, double[] params, int timeoutMs) {
        log.warn("ConfigStatorCurrentLimit not implemented yet.");

        return 0;
    }

    public static int ConfigSupplyCurrentLimitEnable(long handle, boolean enable, int timeoutMs) {
        log.warn("ConfigSupplyCurrentLimitEnable not implemented yet.");

        return 0;
    }

    public static int ConfigStatorCurrentLimitEnable(long handle, boolean enable, int timeoutMs) {
        log.warn("ConfigStatorCurrentLimitEnable not implemented yet.");

        return 0;
    }

    public static int ConfigBrakeCurrentLimitEnable(long handle, boolean enable, int timeoutMs) {
        log.warn("ConfigBrakeCurrentLimitEnable not implemented yet.");

        return 0;
    }

    public static int ConfigGetSupplyCurrentLimit(long handle, double[] toFill, int timeoutMs) {
        log.warn("ConfigGetSupplyCurrentLimit not implemented yet.");

        return 0;
    }

    public static int ConfigGetStatorCurrentLimit(long handle, double[] toFill, int timeoutMs) {
        log.warn("ConfigGetStatorCurrentLimit not implemented yet.");

        return 0;
    }

    public static int SetIntegratedSensorPosition(long handle, double newPos, int timeoutMs) {
        log.warn("SetIntegratedSensorPosition not implemented yet.");

        return 0;
    }

    public static int SetIntegratedSensorPositionToAbsolute(long handle, int timeoutMs) {
        log.warn("SetIntegratedSensorPositionToAbsolute not implemented yet.");

        return 0;
    }

    public static double GetIntegratedSensorPosition(long handle) {
        log.warn("GetIntegratedSensorPosition not implemented yet.");

        return 0;
    }

    public static double GetIntegratedSensorAbsolutePosition(long handle) {
        log.warn("GetIntegratedSensorAbsolutePosition not implemented yet.");

        return 0;
    }

    public static double GetIntegratedSensorVelocity(long handle) {
        log.warn("GetIntegratedSensorVelocity not implemented yet.");

        return 0;
    }

    public static int ConfigIntegratedSensorAbsoluteRange(long handle, int absoluteSensorRange, int timeoutMs) {
        log.warn("ConfigIntegratedSensorAbsoluteRange not implemented yet.");

        return 0;
    }

    public static int ConfigIntegratedSensorOffset(long handle, double offsetDegrees, int timeoutMs) {
        log.warn("ConfigIntegratedSensorOffset not implemented yet.");

        return 0;
    }

    public static int ConfigIntegratedSensorInitializationStrategy(long handle, int initStrategy, int timeoutMs) {
        log.warn("ConfigIntegratedSensorInitializationStrategy not implemented yet.");

        return 0;
    }

}

