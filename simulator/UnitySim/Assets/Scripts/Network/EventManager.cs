using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using FRCSim;
using System.Threading.Tasks;

public class EventManager : MonoBehaviour
{
    public delegate void MotorConfigUpdateEventHandler(MotorConfig motorConfig);
    public delegate void RobotStateUpdateEventHandler(RobotState robotState);
    public delegate void SimulatorRunningEventhandler(bool simulatorRunning);
    public delegate void MotorOutputsUpdatedEventHandler(MotorOutputs motorOutputs);

    public static event MotorConfigUpdateEventHandler MotorConfigUpdated;
    public static event RobotStateUpdateEventHandler RobotStateUpdated;
    public static event SimulatorRunningEventhandler SimulatorRunning;
    public static event MotorOutputsUpdatedEventHandler MotorOutputsUpdated;

    public static EventManager Instance { get; private set; }

    void Awake()
    {
        Instance = this;
        DontDestroyOnLoad(Instance.gameObject);
    }

    public static void PublishMotorConfigUpdateEvent(MotorConfig motorConfig)
    {
        if (MotorConfigUpdated != null)
        {
            MotorConfigUpdated(motorConfig);
        }
    }

    public static void PublishRobotStateUpdateEvent(RobotState robotState)
    {
        if (RobotStateUpdated != null)
        {
            RobotStateUpdated(robotState);
        }
    }

    public static void PublishSimulatorRunningEvent(bool simulatorRunning) {
        if (SimulatorRunning != null) {
            SimulatorRunning(simulatorRunning);
        }
    }

    public static void PublishMotorOutputsUpdatedEvent(MotorOutputs motorOutputs) {
        if (MotorOutputsUpdated != null) {
            // Debug.Log("Publshing new motor outputs");
            MotorOutputsUpdated(motorOutputs);
        }
    }

}
