package frc.robot.simulator.sim.motors;

import frc.robot.simulator.sim.utils.ConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simulates a DC motor using a simplified model appropriate for systems with
 * slow time constants.
 * from:
 * https://github.com/Team254/Sim-FRC-2015/blob/master/src/com/team254/frc2015/sim/DCMotor.java
 */
public class SimDCMotor extends SimMotor {
    private static final Logger log = LoggerFactory.getLogger(SimDCMotor.class);
    // torque motor constant
    public final double kt;

    // angular velocity motor constant
    public final double kv;

    // motor resistance
    public final double resistance;

    // motor inertia
    public final double inertia;

    public SimDCMotor(String model, double kt, double kv, double resistance, double inertia) {
        super();
        this.config = this.config.toBuilder().setModel(model).build();
        this.kt = kt;
        this.kv = kv;
        this.resistance = resistance;
        this.inertia = inertia;
    }

    @Override
    public void step(double voltage, int externalTorque, long deltaTime) {
        // TODO: The load is too low, but it makes the sim super quick to stabalize...
        step(voltage, 2*inertia, /*2*1.20348237e-5*/ externalTorque, deltaTime);
    }

    /**
     * Simulate applying a given voltage and load for a specified period of
     * time.
     *
     * @param voltage        Voltage applied to the motor (V)
     * @param load           Load applied to the motor (kg*m^2)
     * @param externalTorque The external torque applied (ex. due to gravity) (N*m)
     * @param deltaTime      How long the input is applied (ms)
     */
    public void step(double voltage, double load,
                     double externalTorque, double deltaTime) {
        /*
         *
         * Howard Calcs
         * ============
         * torque = inertia * acceleration
         * torque = kt * current
         * voltage = kv * velocity + resistance * current
         * resistance * current = voltage - kv * velocity
         * current = (voltage - kv * velocity) / resistance
         * torque = (kt / resistance) * (voltage - kv * velocity)
         * inertia * acceleration = (kt / resistance) * (voltage - kv * velocity)
         * acceleration = (kt / (inertia * resistance)) * (voltage - kv * velocity)
         */

        // record the voltage applied to the motor for output
        this.voltage = voltage;

        // our deltaTime comes in in ms, usually 20-30ms because we are doing 20ms loops
        // first we convert it to seconds
        double deltaTimeSeconds = deltaTime / 1000.0;

        // load is external, inertia is the motor's inertia
        load += inertia;

        // calculate acceleration using the formula we derived above
        double acceleration = (kt / (load * resistance)) * (voltage - kv * velocity);

        // increase velocity by our acceleration for this time slice
        velocity += acceleration * deltaTimeSeconds;

        // increase position assuming an approximation for acceleration (Howard says it's a zero order hold)
        double deltaPosition = velocity * deltaTimeSeconds + .5 * acceleration * deltaTimeSeconds * deltaTimeSeconds;

        // calculate current using our equations above
        current = (voltage - kv * velocity) / resistance;

        // convert position back into ticks and calculate the change in position
        // use the deltaPosition to update both actual position and sensor position
        // the sensor position can be reset by the robot code, so update it based on the change in position
        sensorPosition += deltaPosition;
        position += deltaPosition;

        // if debug logging is on and this is motor 1, output some information
        if (log.isDebugEnabled() && id == 1) {
            log.debug(String.format("Motor: %d, Voltage: %.2f, Acceleration (revs/sec): %d, Velocity (ticks/second): %d, Position: %d, SensorPosition: %d, Delta Position: %d, DeltaTime (ms): %.0f",
                    id,
                    voltage,
                    ConversionUtils.radiansToRevolutions(acceleration),
                    velocity,
                    position,
                    sensorPosition,
                    deltaPosition,
                    deltaTime));
        }

    }

}
