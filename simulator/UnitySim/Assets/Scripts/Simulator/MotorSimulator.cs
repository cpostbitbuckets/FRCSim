using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using FRCSim;
using UnityEngine;

public class MotorSimulator : MonoBehaviour
{
    private static int MAX_SPEED = 40000;
    private static int VOLTAGE_INCREMENTS = 1023;

    private RobotState RobotState { get; set; } = new RobotState();

    private List<SimMotor> simMotors = new List<SimMotor>();
    private Dictionary<long, SimMotor> simMotorsByHandle = new Dictionary<long, SimMotor>();
    private Dictionary<int, SimMotor> simMotorsById = new Dictionary<int, SimMotor>();

    private bool _simulatorRunning = false;
    public bool SimulatorRunning
    {
        get => _simulatorRunning;
        private set
        {
            _simulatorRunning = value;
            EventManager.PublishSimulatorRunningEvent(_simulatorRunning);
        }
    }

    /// <summary>
    /// Register for events on startup
    /// </summary>
    public void OnEnable()
    {
        EventManager.RobotStateUpdated += RobotStateUpdated;
        EventManager.MotorConfigUpdated += MotorConfigUpdated;
    }

    /// <summary>
    /// Unregister for events on teardown
    /// </summary>
    public void OnDisable()
    {
        SimulatorRunning = false;
        EventManager.RobotStateUpdated -= RobotStateUpdated;
        EventManager.MotorConfigUpdated -= MotorConfigUpdated;
    }

    #region Events

    private void RobotStateUpdated(RobotState robotState)
    {
        RobotState = robotState;
    }

    private void MotorConfigUpdated(MotorConfig motorConfig)
    {
        UpdateMotorConfig(motorConfig);
    }

    #endregion

    public void Update()
    {
        if (RobotState.Initialized)
        {
            if (!SimulatorRunning)
            {
                Debug.Log("Starting Motor Simulator");
                SimulatorRunning = true;
            }
            MotorOutputs outputs = new MotorOutputs();
            foreach (SimMotor motor in simMotors)
            {
                SimulateMotor(motor);
                outputs.MotorOutput.Add(new FRCSim.MotorOutputs.Types.MotorOutput
                {
                    Id = motor.Id,
                    Handle = motor.Handle,
                    Output = motor.Output,
                    Velocity = motor.Velocity,
                    Position = motor.Position,
                    LastError = motor.LastError,
                    IntegralState = motor.IntegralState
                });
            }

            // Notify any listeners that we have new motor outputs
            EventManager.PublishMotorOutputsUpdatedEvent(outputs);
        }
        else
        {
            if (SimulatorRunning)
            {
                Debug.Log("Stopping Motor Simulator");
            }

            SimulatorRunning = false;
        }
    }

    // Build a MotorOutputs protobuf from our simulated motors
    public MotorOutputs GetMotorOutputs()
    {
        MotorOutputs motorOutputs = new MotorOutputs();
        foreach (SimMotor simMotor in simMotors)
        {
            var output = new FRCSim.MotorOutputs.Types.MotorOutput
            {
                Id = simMotor.Id,
                Handle = simMotor.Handle,
                Output = simMotor.Output,
                Position = simMotor.Position,
                SensorPosition = simMotor.SensorPosition,
                Velocity = simMotor.Velocity,
                IntegralState = simMotor.IntegralState,
                LastError = simMotor.LastError
            };
            motorOutputs.MotorOutput.Add(output);
        }

        return motorOutputs;
    }

    public void UpdateRobotState(RobotState request)
    {
        RobotState = request;
    }

    /// <summary>
    /// Update the motor config for our sim motor
    /// </summary>
    /// <param name="config"></param>
    /// <returns></returns>
    public void UpdateMotorConfig(MotorConfig config)
    {
        SimMotor simMotor;
        if (!simMotorsByHandle.TryGetValue(config.Handle, out simMotor))
        {
            simMotor = new SimMotor()
            {
                Id = config.Id,
                Handle = config.Handle
            };
            simMotors.Add(simMotor);
            simMotorsByHandle.Add(simMotor.Handle, simMotor);
            simMotorsById.Add(simMotor.Id, simMotor);
        }

        simMotor.Config = config;

        if (config.FollowingId != 0)
        {
            var following = simMotorsById[config.FollowingId];
            simMotor.Following = following;
        }
        else
        {
            simMotor.Following = null;
        }

    }

    private void SimulateMotor(SimMotor motor)
    {
        double output = 0;
        double acceleration = 0; // TODO: this is too unknown

        if (motor.Following != null)
        {
            output = motor.Output;
            acceleration = motor.Following.Config.MotionMagicAcceleration;
        }
        else
        {
            acceleration = motor.Config.MotionMagicAcceleration;
            ControlMode mode = (ControlMode)motor.Config.ControlMode;
            switch (mode)
            {
                case ControlMode.PercentOutput:
                    output = motor.Config.TargetOutput;
                    break;
                case ControlMode.Position:
                    // compute position based on our target
                    output = CalculateFeedbackOutput(motor, motor.Position, motor.Config.TargetPosition);
                    break;
                case ControlMode.Velocity:
                    // compute position based on our target
                    output = CalculateFeedbackOutput(motor, motor.Velocity, motor.Config.TargetVelocity);
                    break;
                case ControlMode.Current:
                    break;
                case ControlMode.Follower:
                    break;
                case ControlMode.MotionProfile:
                    break;
                case ControlMode.MotionMagic:
                    output = CalculateMotionMagicOutput(motor, motor.Position, motor.Config.TargetPosition);
                    break;
                case ControlMode.MotionProfileArc:
                    break;
                case ControlMode.Disabled:
                    output = 0;
                    break;
            }
        }

        // clamp the output between -1 and 1
        output = Mathf.Clamp((float)output, -1, 1);

        float timePer100ms = Time.deltaTime * 1000.0f / 100.0f;

        // our velocity is in ticks per 100ms, delta time is the time in ms since the last update
        // so only update our position in per 100ms increments
        int velocity = (int)(MAX_SPEED * output);
        int positionDiff = (int)(velocity * timePer100ms);

        // don't accelerate faster than we are configured
        if (Math.Abs(positionDiff) > acceleration * (timePer100ms))
        {
            positionDiff = (int)(float)(acceleration * (timePer100ms) * Mathf.Sign(positionDiff));
        }
        double position = motor.Position + positionDiff;

        motor.Output = output;
        motor.Position = position;
        motor.Velocity = velocity;
    }

    /**
     * Calculate the output percentage based on where we are (current) and our target
     *
     * @param currentPosition
     * @param targetPosition
     * @return
     */
    protected double CalculateFeedbackOutput(SimMotor motor, double currentPosition, double targetPosition)
    {
        double error = targetPosition - currentPosition;
        double absError = Math.Abs(error);

        return CalculatePIDOutput(motor, error, absError, MAX_SPEED);
    }


    double CalculateMotionMagicOutput(SimMotor motor, double currentPosition, double targetPosition)
    {
        // convert any of these error/deltaError units to between -1 an 1
        double error = (targetPosition - currentPosition);
        double absError = Math.Abs(error);

        // don't go any faster than motionMagicCruiseVelocity, unless the error is smaller
        // that the motionMagicCruiseVelocity
        double targetVelocity = motor.Config.MotionMagicCruiseVelocity * Mathf.Sign((float)error);
        if (absError < Math.Abs(targetVelocity))
        {
            targetVelocity = error;
        }

        return CalculatePIDOutput(motor, error, absError, targetVelocity);
    }

    /**
     * Run our target velocity through the simulated PID controller to get the new voltage output from the PID
     * @param error The current error, in ticks
     * @param absError The absolute value of the error, used for checking iZone
     * @param targetVelocity The max velocity we want, in ticks (either the motor's max speed or the MM max speed)
     * @return A new output voltage value. Note: this must be clamped between -1 and 1
     */
    private double CalculatePIDOutput(SimMotor motor, double error, double absError, double targetVelocity)
    {
        double deltaError = error - motor.LastError;
        motor.LastError = error;
        motor.IntegralState = motor.IntegralState + error;

        FRCSim.MotorConfig.Types.FPID fpid = motor.Config.Fpid[motor.Config.CurrentPidProfile];

        // if we are within the izone, don't do any output
        if (absError <= fpid.IZone)
        {
            return 0;
        }

        double pComp = fpid.P * error;
        double iComp = fpid.I * motor.IntegralState;
        double dComp = fpid.D * deltaError;
        double fComp = fpid.F * targetVelocity;

        // all of our PID constants are in terms of 1/1023 units of voltage
        return (pComp + iComp + dComp + fComp) / VOLTAGE_INCREMENTS;
    }

}
