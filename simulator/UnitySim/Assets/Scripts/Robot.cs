using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using FRCSim;
using UnityEngine.EventSystems;
using System;

public class Robot : MonoBehaviour
{
    public GameObject leftFrontWheel;
    public GameObject leftRearWheel;
    public GameObject rightFrontWheel;
    public GameObject rightRearWheel;

    private Dictionary<int, GameObject> wheelsById;

    private List<SimMotor> simMotors = new List<SimMotor>();
    private Dictionary<long, SimMotor> simMotorsByHandle = new Dictionary<long, SimMotor>();
    private Dictionary<int, SimMotor> simMotorsById = new Dictionary<int, SimMotor>();

    void OnEnable()
    {
        wheelsById = new Dictionary<int, GameObject>()
        {
            [1] = leftFrontWheel,
            [2] = leftRearWheel,
            [3] = rightFrontWheel,
            [4] = rightRearWheel,
        };

        EventManager.MotorConfigUpdated += MotorConfigUpdated;
    }

    void OnDisable()
    {
        EventManager.MotorConfigUpdated -= MotorConfigUpdated;
    }

    private void MotorConfigUpdated(MotorConfig motorConfig)
    {
        SimMotor simMotor;
        if (!simMotorsByHandle.TryGetValue(motorConfig.Handle, out simMotor))
        {
            simMotor = new SimMotor()
            {
                Id = motorConfig.Id,
                Handle = motorConfig.Handle
            };
            simMotors.Add(simMotor);
            simMotorsByHandle.Add(simMotor.Handle, simMotor);
            simMotorsById.Add(simMotor.Id, simMotor);
        }

        simMotor.Config = motorConfig;

        if (motorConfig.FollowingId != 0)
        {
            SimMotor following;
            if (simMotorsById.TryGetValue(motorConfig.FollowingId, out following))
            {
                simMotor.Following = following;
            }
        }
        else
        {
            simMotor.Following = null;
        }
    }

    void FixedUpdate()
    {
        foreach (SimMotor simMotor in simMotors)
        {
            var output = (float)simMotor.Config.TargetVelocity;
            if (simMotor.Config.FollowingId != 0)
            {
                SimMotor following;
                if (simMotorsById.TryGetValue(simMotor.Config.FollowingId, out following))
                {
                    output = (float)following.Config.TargetVelocity;
                }
            }

            GameObject wheel;
            if (wheelsById.TryGetValue(simMotor.Id, out wheel))
            {
                Rigidbody body;
                if (wheel.TryGetComponent<Rigidbody>(out body))
                {
                    body.AddRelativeTorque(0, -output * 100.0f, 0);
                }
                else
                {
                    Debug.Log("No wheel component for motor " + simMotor.Config.Id);
                }
            }
        }
    }

}
