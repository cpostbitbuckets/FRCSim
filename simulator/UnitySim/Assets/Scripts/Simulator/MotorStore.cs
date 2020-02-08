using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading.Tasks;
using FRCSim;
using UnityEngine;

public class MotorStore : MonoBehaviour
{
    private RobotState RobotState { get; set; } = new RobotState();

    private List<SimMotor> simMotors = new List<SimMotor>();
    private Dictionary<long, SimMotor> simMotorsByHandle = new Dictionary<long, SimMotor>();
    private Dictionary<int, SimMotor> simMotorsById = new Dictionary<int, SimMotor>();

    /// <summary>
    /// Register for events on startup
    /// </summary>
    public void OnEnable()
    {
        EventManager.RobotStateUpdated += RobotStateUpdated;
    }

    /// <summary>
    /// Unregister for events on teardown
    /// </summary>
    public void OnDisable()
    {
        EventManager.RobotStateUpdated -= RobotStateUpdated;
    }

    #region Events

    private void RobotStateUpdated(RobotState robotState)
    {
        RobotState = robotState;
    }

    #endregion

    public SimMotor CreateOrUpdateMotor(MotorConfig motorConfig)
    {
        SimMotor simMotor;
        if (!simMotorsByHandle.TryGetValue(motorConfig.Handle, out simMotor))
        {
            simMotor = new SimMotor()
            {
                Id = motorConfig.Id,
                Handle = motorConfig.Handle
            };
            // initialize the MotorOutput
            simMotors.Add(simMotor);
            simMotorsByHandle.Add(simMotor.Handle, simMotor);
            simMotorsById.Add(simMotor.Id, simMotor);
            Debug.Log("Created motor: " + simMotor.Id);

        }

        simMotor.Config = motorConfig;

        if (motorConfig.FollowingId != 0 && simMotor.Following == null)
        {
            SimMotor following;
            if (simMotorsById.TryGetValue(motorConfig.FollowingId, out following))
            {
                simMotor.Following = following;
                Debug.Log("motor " + motorConfig + " following " + following.Config);
            }
        }
        else
        {
            simMotor.Following = null;
        }

        return simMotor;
    }

    /// <summary>
    /// Get a motor by its handle
    /// </summary>
    /// <param name="handle"></param>
    /// <returns></returns>
    public SimMotor GetSimMotor(long handle)
    {
        return simMotorsByHandle[handle];
    }

    public void Update()
    {
        if (RobotState.Initialized)
        {
            MotorOutputs outputs = new MotorOutputs();
            foreach (SimMotor motor in simMotors)
            {
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

}
