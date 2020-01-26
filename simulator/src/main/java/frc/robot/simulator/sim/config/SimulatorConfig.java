package frc.robot.simulator.sim.config;

import frc.robot.simulator.sim.motors.Vendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This configuration configures how the simulator itself runs
 * This is loaded from the robot project and used to configure the motors for a robot
 * Each robot's motors will have different physics and require different config
 */
public class SimulatorConfig {

    public static class DriveBase {
        // the mass of a test robot in kg
        public double mass = 39.3051396;

        // radius of the robot and wheels in meters
        public double radius = 0.3145536;
        public double wheelRadius = 0.07762;

        // moments of inertia
        public double robotInertia = 2.1621037;
        public double wheelInertia = .0001;

        // viscous friction of drive motors
        public double viscousFriction = 0.04666464678901319;
    }

    public static class Transmission {
        public List<Motor> motors = new ArrayList<>();
        public double gearRatio = 1;
        public double efficiency = 1;

        public Transmission() {
        }

        public Transmission(Motor motor) {
            motors.add(motor);
            motor.transmission = this;
        }
    }

    public static Motor defaultCTRE(int id) {
        Motor motor = new Motor(id);
        motor.name = "";
        motor.model = "BAG";

        // from https://www.vexrobotics.com/217-3351.html#Size
        motor.stallTorque = .4;
        motor.stallCurrent = 53;
        motor.voltage = 12;
        motor.maxRPM = 13180;
        motor.mass = .32;
        motor.diameter = .0404;

        return motor;
    }

    public static Motor defaultRev(int id) {
        Motor motor = new Motor(id);
        motor.name = "";
        motor.model = "NEO";

        // from http://www.revrobotics.com/rev-21-1650/
        motor.stallTorque = 2.6;
        motor.stallCurrent = 105;
        motor.voltage = 12;
        motor.maxRPM = 5676;
        motor.mass = .425;
        motor.diameter = .06;

        return motor;
    }

    public static class Motor {
        public int id;
        public String name;
        public String model;
        // is this a left drive or right drive motor?
        public boolean leftDrive;
        public boolean rightDrive;

        // motor config can either be physical characteristics, or
        // specific kt, kv, resistance, and inertia values
        public double stallTorque;
        public double stallCurrent;
        public double voltage;
        public int maxRPM; 
        public double mass;
        public double diameter;
        
        public double kt;
        public double kv;
        public double resistance;
        public double inertia;

        public int encoderCountsPerRevolution = 1024;

        transient public Transmission transmission;

        public Motor() {}
        public Motor(int id) {
            this.id = id;
        }
    }

    public boolean hideFollowers = true;
    public DriveBase driveBase = new DriveBase();
    public List<Transmission> transmissions = new ArrayList<>();

    private final Map<Integer, Motor> motorsById = new HashMap<>();

    /**
     * Add a new default motor (if not found in config) with a default name
     * @param id
     * @param vendor
     * @return
     */
    public Motor addNewDefaultMotor(int id, Vendor vendor) {
        Motor motor;
        if (vendor == Vendor.Rev) {
            motor = defaultRev(id);
        } else {
            motor = defaultCTRE(id);
        }
        motor.name = motor.model + " (" + id + ")";

        transmissions.add(new Transmission(motor));
        motorsById.put(id, motor);
        return motor;
    }

    /**
     * Initialize this config after being loaded from yaml
     */
    public void init() {
        motorsById.clear();
        transmissions.stream().forEach(t -> t.motors.stream().forEach(m -> {
            motorsById.put(m.id, m);
            m.transmission = t;
        }));
    }

    public Motor getConfigForMotor(int id) {
        return motorsById.get(id);
    }
}
