using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using FRCSim;
using UnityEngine.EventSystems;
using System;

/// <summary>
/// The Motor behavior is applied to an object in the scene that has a motor attached to it
/// </summary>
public class Motor : MonoBehaviour
{
    /// <summary>
    /// Each Motor needs to know about the MotorSimulator so we can get access to followers
    /// </summary>
    public MotorStore motorStore;

    /// <summary>
    /// The ID of the motor from the server that this game object is associated with
    /// </summary>
    public int motorId;

    /// <summary>
    /// The gear ratio for this motor, adjust this to slow down or speed up the motor
    /// </summary>
    public float gearRatio = 242.0f / 27.0f;

    public float stallTorque = 10;

    // this is the sim motor that comes from the robot
    private SimMotor simMotor;

    /// <summary>
    /// The rigid body we apply forces to
    /// </summary>
    private Rigidbody body;

    private ConfigurableJoint joint;

    void OnEnable()
    {
        body = GetComponent<Rigidbody>();
        joint = GetComponent<ConfigurableJoint>();

        EventManager.MotorConfigUpdated += MotorConfigUpdated;
    }

    void OnDisable()
    {
        EventManager.MotorConfigUpdated -= MotorConfigUpdated;
    }

    /// <summary>
    /// When this component gets a motor update, if it's the motor assigned
    /// to this component, update the SimMotor object
    /// </summary>
    /// <param name="motorConfig"></param>
    private void MotorConfigUpdated(MotorConfig motorConfig)
    {
        if (motorId != motorConfig.Id)
        {
            // this motor isn't assigned to us, ignore the update
            return;
        }

        simMotor = motorStore.CreateOrUpdateMotor(motorConfig);
    }

    void FixedUpdate()
    {
        // if we don't have a simMotor assigned to this component, don't
        // do anything
        if (simMotor != null) { 
            // find the motor we look to for commands
            SimMotor leader = simMotor;
            if (simMotor.Following != null)
            {
                leader = simMotor.Following;
            }

            double radsPerSecond = leader.Config.TargetVelocity;
            double radsPosition = leader.Config.TargetPosition;
            switch (leader.Config.ControlMode)
            {
                case MotorConfig.Types.ControlMode.PercentOutput:
                    float output = (float)(stallTorque * leader.Config.TargetOutput);
                    body.AddRelativeTorque(output, output, output);
                    break;
                case MotorConfig.Types.ControlMode.MotionVelocity:
                case MotorConfig.Types.ControlMode.Velocity:
                    break;
                case MotorConfig.Types.ControlMode.Position:
                    break;
            }

        }
    }

    void OnUpdate()
    {
        if (simMotor != null)
        {
            // TODO: Convert force to -1 to 1 output...
            //simMotor.Output;
            //simMotor.Velocity = joint.angvelocity / 180.0f;
            //simMotor.Position = joint.angle / 180.0f;
        }
    }

    /// <summary>
    /// This will run a motor through a pid loop to determine the output
    /// </summary>
    /// <param name="motor"></param>
    /// <param name="setPoint"></param>
    /// <param name="pv"></param>
    /// <returns></returns>
    double RunPid(SimMotor motor, double setPoint, double pv)
    {
        MotorConfig.Types.FPID fpid = motor.Config.Fpid[motor.Config.CurrentPidProfile];

        // calculate p
        double error = setPoint - pv;
        double pComp = error * fpid.P;

        // calculate the (iState or integralState)
        if (Math.Abs(error) <= fpid.IZone || fpid.IZone == 0.0f)
        {
            motor.IntegralState = motor.IntegralState + (error * fpid.I);
        }
        else
        {
            motor.IntegralState = 0;
        }

        // calculate the d component
        double dComp = (error - motor.LastError);
        motor.LastError = error;
        dComp *= fpid.D;

        // calculate the f component
        double fComp = setPoint * fpid.F;

        // for a PID controller, the output is the sum of the components
        double output = pComp + motor.IntegralState + dComp + fComp;

        return output;
    }

}
