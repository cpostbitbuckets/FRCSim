package frc.robot.simulator.sim.motors;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import frc.robot.simulator.network.proto.RobotProto;

public class SimMotor {
    private static final Logger log = LoggerFactory.getLogger(SimMotor.class);

    protected int id;
    protected long handle;

    protected RobotProto.MotorConfig config = RobotProto.MotorConfig.newBuilder().build();

    // the output of the motor, between -1.0 and 1.0
    public double output = 0;
    public double velocity = 0;
    public double position = 0;
    public double voltage = 0;
    public double current = 0;
    public double force;
    public double sensorPosition = 0;
    public double integralState = 0;
    public double lastError = 0;

    // whether these are left or right drive motors
    public boolean leftDriveMotor = false;
    public boolean rightDriveMotor = false;

    public SimMotor() {

    }

    /**
     * Initialize the motor and motor config with a handle, id, name, etc.
     * @param handle
     * @param id
     * @param name
     * @param model
     */
    public void init(long handle, int id, String name, String model, int encoderTicksPerRevolution) {
        // Talons do some funkiness with the ID vs what we will use as the handle
        this.id = id;
        this.handle = handle;

        // update the config id and handle when we create this motor
        this.config = this.config.toBuilder()
                .setHandle(this.handle)
                .setId(this.id)
                .setName(name)
                .setModel(model)
                .addFpid(RobotProto.MotorConfig.FPID.newBuilder().build()) // start with a couple empty FPID slots
                .addFpid(RobotProto.MotorConfig.FPID.newBuilder().build())
                .addFpid(RobotProto.MotorConfig.FPID.newBuilder().build())
                .addFpid(RobotProto.MotorConfig.FPID.newBuilder().build())
                .addCustomParams(0)
                .addCustomParams(0)
                .setEncoder(RobotProto.MotorConfig.Encoder.NotSet)
                .setEncoderCountsPerRevolution(encoderTicksPerRevolution)
                .build();
    }

    /**
     * Simulate applying a given voltage and load for a specified period of
     * time.
     *
     * @param voltage Voltage applied to the motor (V)
     * @param load           Load applied to the motor (kg*m^2)
     * @param externalTorque The external torque applied (ex. due to gravity) (N*m)
     * @param deltaTime      How long the input is applied (s)
     */
    public void step(double voltage, double load, double externalTorque, double deltaTime) {
        throw new NotImplementedException("The base SimMotor does not implement a step function. Use a SimDCMotor instead");
    }

    public void step(double voltage, int externalTorque, long deltaTime) {
        throw new NotImplementedException("The base SimMotor does not implement a step function. Use a SimDCMotor instead");
    }

    public double getLastError() {
       // return the error in ticks
        return lastError;
    }

    /**
     * Set the vendor based on a string, either CTRE or Rev
     * @param vendor
     */
    public void setVendor(Vendor vendor) {
        this.setConfig(this.getConfig().toBuilder().setVendor(RobotProto.MotorConfig.Vendor.valueOf(vendor.toString())).build());
    }

    public Vendor getVendor() {
        switch(this.getConfig().getVendor()) {

            case CTRE:
                return Vendor.CTRE;
            case Rev:
                return Vendor.Rev;
            case UNRECOGNIZED:
            default:
                throw new IllegalArgumentException("Vendor not set for SimMotor");
        }
    }

    public int getId() {
        return id;
    }

    public long getHandle() {
        return handle;
    }

    public void setLastError(double lastError) {
        this.lastError = lastError;
    }

    public double getOutput() {
        return output;
    }

    public void setOutput(double output) {
        this.output = output;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getSensorPosition() {
        return sensorPosition;
    }

    public void setSensorPosition(double sensorPosition) {
        this.sensorPosition = sensorPosition;
    }

    public double getIntegralState() {
        return integralState;
    }

    public void setIntegralState(double integralState) {
        this.integralState = integralState;
    }

    public RobotProto.MotorConfig getConfig() {
        return config;
    }

    public void setConfig(RobotProto.MotorConfig config) {
        this.config = config;
    }

    public double getVoltage() {
        return voltage;
    }

    public double getCurrent() {
        return current;
    }

    public boolean isLeftDriveMotor() {
        return leftDriveMotor;
    }

    public void setLeftDriveMotor(boolean leftDriveMotor) {
        this.leftDriveMotor = leftDriveMotor;
    }

    public boolean isRightDriveMotor() {
        return rightDriveMotor;
    }

    public void setRightDriveMotor(boolean rightDriveMotor) {
        this.rightDriveMotor = rightDriveMotor;
    }
}
