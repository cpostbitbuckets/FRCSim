using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TestRobot : MonoBehaviour
{
    public MotorStore motorStore;
    private SimMotor leftFrontMotor;
    private SimMotor leftRearMotor;
    private SimMotor rightFrontMotor;
    private SimMotor rightRearMotor;

    // Start is called before the first frame update
    void Start()
    {
        EventManager.PublishMotorConfigUpdateEvent(new FRCSim.MotorConfig()
        {
            Id = 1,
            Handle = 100001,
        });
        EventManager.PublishMotorConfigUpdateEvent(new FRCSim.MotorConfig()
        {
            Id = 2,
            Handle = 100002,
            FollowingId = 1
        });
        EventManager.PublishMotorConfigUpdateEvent(new FRCSim.MotorConfig()
        {
            Id = 3,
            Handle = 100003,
        });
        EventManager.PublishMotorConfigUpdateEvent(new FRCSim.MotorConfig()
        {
            Id = 4,
            Handle = 100004,
            FollowingId = 3
        });

        leftFrontMotor = motorStore.GetSimMotor(100001);
        leftRearMotor = motorStore.GetSimMotor(100002);
        rightFrontMotor = motorStore.GetSimMotor(100003);
        rightRearMotor = motorStore.GetSimMotor(100004);

    }

    // Update is called once per frame
    void Update()
    {
        float yAxisLeft = Input.GetAxisRaw("Vertical");
        float xAxisLeft = Input.GetAxisRaw("Horizontal");

        float leftSpeed = yAxisLeft + xAxisLeft;
        float rightSpeed = yAxisLeft - xAxisLeft;

        if (leftFrontMotor.Config.TargetOutput != leftSpeed)
        {
            leftFrontMotor.Config.TargetOutput = leftSpeed;
            EventManager.PublishMotorConfigUpdateEvent(leftFrontMotor.Config);
        }
        if (leftRearMotor.Config.TargetOutput != leftSpeed)
        {
            leftRearMotor.Config.TargetOutput = leftSpeed;
            EventManager.PublishMotorConfigUpdateEvent(leftRearMotor.Config);
        }
        if (rightFrontMotor.Config.TargetOutput != rightSpeed)
        {
            rightFrontMotor.Config.TargetOutput = rightSpeed;
            EventManager.PublishMotorConfigUpdateEvent(rightFrontMotor.Config);
        }
        if (rightRearMotor.Config.TargetOutput != rightSpeed)
        {
            rightRearMotor.Config.TargetOutput = rightSpeed;
            EventManager.PublishMotorConfigUpdateEvent(rightRearMotor.Config);
        }


    }
}
