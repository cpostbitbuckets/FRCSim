package frc.robot.simulator.sim.field.physics;

import frc.robot.simulator.sim.RobotPosition;
import frc.robot.simulator.sim.SimSPI;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.field.FieldSim;
import frc.robot.simulator.sim.ic2.SimNavX;
import frc.robot.simulator.sim.motors.MotorStore;
import frc.robot.simulator.sim.motors.SimMotor;

import java.util.ArrayList;

import com.google.inject.Inject;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.nonstiff.DormandPrince853Integrator;

import edu.wpi.first.wpilibj.SPI;



/**
 * Uses a more complete description of the underlying physics to calculate the
 * position of the robot, and solves the robot's position differential equation
 * using a Dormand-Prince integrator instead of an Euler approximation
 */
public class PhysicsSim extends FieldSim {
    // differential equation that describes robot's motions
    private final FirstOrderDifferentialEquations ode;
    // ODE solver
    private final DormandPrince853Integrator integrator;

    // keep track of motors on both sides... will need an average voltage
    private final ArrayList<SimMotor> leftMotors;
    private final ArrayList<SimMotor> rightMotors;
    private boolean createdMotors = false;

    // average voltage applies to motors on left and right sides
    private double VL = 0;
    private double VR = 0;

    private double vL = 0;
    private double vR = 0;



    @Inject
    public PhysicsSim(MotorStore motorStore, SimulatorConfig simulatorConfig) {
        super(motorStore, simulatorConfig);

        leftMotors = new ArrayList<SimMotor>();
        rightMotors = new ArrayList<SimMotor>();

        // first order but using a state space representation so you can do whatever you want
        // keeps track of:
        // 0. x
        // 1. y
        // 2. theta = -heading (because NavX IMU has a different orientation)
        // 3. left wheel velocities
        // 4. right wheel velocities
        // 5. change in position of left wheels
        // 6. change in position of right wheels
        // this will override the wheel velocity calculations that are already done in order
        // to provide 8th order estimates and not 1st order
        ode = new FirstOrderDifferentialEquations() {
			@Override
			public int getDimension() {
				return 7; // 7 states
			}

			@Override
			public void computeDerivatives(double t, double[] state, double[] stateDot) throws MaxCountExceededException, DimensionMismatchException {
                double theta = state[2];
                double vL = state[3];
                double vR = state[4];

                // v = (vL + vR) / 2, linear velocity
                // vx = v cos theta, so that's x derivative
                stateDot[0] = (vL + vR) / 2 * Math.cos(theta);
                // vy = v sin theta, so that's y derivative
                stateDot[1] = (vL + vR) / 2 * Math.sin(theta);
                // differential drive
                stateDot[2] = (vR - vL) / (2 * simulatorConfig.driveBase.radius);
                // fancy derived physics equations
                // to see why it can be written in this form, see
                // section 14.5 of the Controls Engineering in FRC book:
                // https://file.tavsys.net/control/controls-engineering-in-frc.pdf
                // equations 14.29 and .30 show the left and right accelerations
                // are a linear combination of left and right velocities and voltages
                stateDot[3] = 
                    simulatorConfig.driveBase.ssAc[0][0] * vL
                    + simulatorConfig.driveBase.ssAc[0][1] * vR
                    + simulatorConfig.driveBase.ssBc[0][0] * VL
                    + simulatorConfig.driveBase.ssBc[0][1] * VR;
                stateDot[4] = 
                    simulatorConfig.driveBase.ssAc[1][0] * vL
                    + simulatorConfig.driveBase.ssAc[1][1] * vR
                    + simulatorConfig.driveBase.ssBc[1][0] * VL
                    + simulatorConfig.driveBase.ssBc[1][1] * VR;
                // left and right positions
                // by definition, theta' = omega
                stateDot[5] = vL;
                stateDot[6] = vR;
			}
        };

        double[] absTol = new double[] {
            0.01 / 250, 0.01 / 250, // max error of 1cm / 5 seconds (about 50 iterations per second)
            1*Math.PI / 180 / 250, // max error of 1 deg / 5 seconds (about 50 iterations per second)
            0.001/50, 0.001/50, // max error of 1 mm/s / s
            0.001/50, 0.001/50 // max error of 1 mm / s
        };

        integrator = new DormandPrince853Integrator(0.001, 0.02,
            absTol,
            absTol // doesn't really matter tbh
        );
    }



    private void createMotors() {
        if (createdMotors) {
            return;
        }

        // add to the left and right motors
        for (SimMotor simMotor : motorStore.getSimMotorsSorted()) {
            if (simMotor.isLeftDriveMotor()) {
                leftMotors.add(simMotor);
            } else if (simMotor.isRightDriveMotor()) {
                rightMotors.add(simMotor);
            }
        }

        createdMotors = true;
    }

    @Override
    protected RobotPosition supplyRobotPosition() {
        return new RobotPosition(RobotPosition.Type.Physics);
    }

    @Override
    public void step(double deltaTime) {
        // calculate average voltage across left and right motors
        VL = 0;
        VR = 0;

        createMotors();

        for (int i = 0; i < leftMotors.size(); i++) {
            VL += leftMotors.get(i).getVoltage();
        }

        for (int i = 0; i < rightMotors.size(); i++) {
            VR -= rightMotors.get(i).getVoltage();
        }

        VL /= leftMotors.size();
        VR /= rightMotors.size();



        // conversion Factor from rad/s to m/s
        double f = simulatorConfig.driveBase.wheelRadius * 2 * Math.PI / simulatorConfig.driveBase.gearRatio;

        // convert from rad/s to m/s
        double vL0 = leftMotors.get(0).getVelocity() * f;
        double vR0 = -rightMotors.get(0).getVelocity() * f; // flip because differential drive

        double[] state = new double[] {
            robotPosition.x,
            robotPosition.y,
            -robotPosition.heading, // different coordinate system
            vL,
            vR,
            // changes in position to use for every left and right motor
            // could do position but they should all move by the same amount
            // and I don't want more states
            0,
            0
        };

        // integrate for deltaTime seconds
        integrator.integrate(ode, 0, state, deltaTime, state);

        // update position and heading with new info
        robotPosition.x = state[0];
        robotPosition.y = state[1];
        robotPosition.heading = -state[2];

        // give NavX IMU new data
        SimNavX simNavX = SimSPI.getNavX(SPI.Port.kMXP.value);
        if (simNavX != null) {
            float degrees = (float)((robotPosition.heading - simulatorConfig.startPosition.heading) * 360 / (Math.PI * 2));

            // degrees are between 0 and 360
            if (degrees < 0) {
                degrees = 360 - (Math.abs(degrees) % 360);
            } else {
                degrees = degrees % 360;
            }
            simNavX.heading = degrees;
        }

        vL = state[3];
        vR = state[4];

        // // update left and right velocities and positions with new info
        // for (int i = 0; i < leftMotors.size(); i++) {
        //     leftMotors.get(i).setVelocity(state[3] / f); // convert to rad/s
        //     // convert to rad, add initial position bc it returns a change
        //     leftMotors.get(i).setPosition(leftMotors.get(i).getPosition() + state[5] / f);
        // }

        // for (int i = 0; i < rightMotors.size(); i++) {
        //     // invert signal because it's a differential drive so right motor is backward
        //     rightMotors.get(i).setVelocity(-state[4] / f); // convert to rad/s
        //     // convert to rad, add initial position bc it returns a change
        //     rightMotors.get(i).setPosition(rightMotors.get(i).getPosition() - state[6] / f);
        // }
    }
    
}