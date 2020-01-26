using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using FRCSim;
using System.Threading.Tasks;
using System;

public class NetworkManager : MonoBehaviour
{
    // public static NetworkManager Instance { get; private set; }

    public Client client;
    public Server server;

    void Awake()
    {
        client = gameObject.AddComponent<Client>();
        server = gameObject.AddComponent<Server>();
        DontDestroyOnLoad(gameObject);
    }

    void OnEnable()
    {
        Debug.Log("Enabling NetworkManager");

        EventManager.RobotStateUpdated += RobotStateUpdated;
        EventManager.MotorOutputsUpdated += MotorOutputsUpdated;

        // server.Start();
    }

    void OnDisable()
    {
        Debug.Log("Disabling NetworkManager");
        // unsubscribe from events
        EventManager.MotorOutputsUpdated -= MotorOutputsUpdated;
        EventManager.RobotStateUpdated -= RobotStateUpdated;

    }

    private void RobotStateUpdated(RobotState robotState)
    {
    }

    private void MotorOutputsUpdated(MotorOutputs motorOutputs)
    {
        client.SendUpdatedMotorOutputs(motorOutputs);
    }

    // Update is called once per frame
    void Update()
    {
        if (client != null && client.Connected)
        {
            float xAxis = Input.GetAxisRaw("Horizontal");
            float yAxis = Input.GetAxisRaw("Vertical");

            client.SendInput(xAxis, yAxis);
        }
    }

}
