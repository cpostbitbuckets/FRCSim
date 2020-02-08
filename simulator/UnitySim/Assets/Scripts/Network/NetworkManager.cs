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

        EventManager.MotorOutputsUpdated += MotorOutputsUpdated;

        // server.Start();
    }

    void OnDisable()
    {
        Debug.Log("Disabling NetworkManager");
        // unsubscribe from events
        EventManager.MotorOutputsUpdated -= MotorOutputsUpdated;

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

            var inputRequest = new InputRequest { Id = 0 };

            float xAxisLeft = Input.GetAxisRaw("Horizontal");
            float yAxisLeft = Input.GetAxisRaw("Vertical");
            float xAxisRight = 0;// Input.GetAxisRaw("4th Axis");
            float yAxisRight = 0;// Input.GetAxisRaw("5th Axis");

            // key overrides
            if (Input.GetKey(KeyCode.W))
            {
                yAxisLeft = 1;
            }
            else if (Input.GetKey(KeyCode.S))
            {
                yAxisLeft = -1;
            }

            if (Input.GetKey(KeyCode.A))
            {
                xAxisLeft = -1;
            }
            else if (Input.GetKey(KeyCode.D))
            {
                xAxisLeft = 1;
            }

            // key overrides
            if (Input.GetKey(KeyCode.I))
            {
                yAxisRight = 1;
            }
            else if (Input.GetKey(KeyCode.K))
            {
                yAxisRight = -1;
            }

            if (Input.GetKey(KeyCode.J))
            {
                xAxisRight = -1;
            }
            else if (Input.GetKey(KeyCode.L))
            {
                xAxisRight = 1;
            }

            inputRequest.Axes.Add(xAxisLeft);
            inputRequest.Axes.Add(yAxisLeft);
            inputRequest.Axes.Add(xAxisRight);
            inputRequest.Axes.Add(yAxisRight);

            inputRequest.Buttons.Add(Input.GetKey(KeyCode.Alpha1));
            inputRequest.Buttons.Add(Input.GetKey(KeyCode.Alpha2));
            inputRequest.Buttons.Add(Input.GetKey(KeyCode.Alpha3));
            inputRequest.Buttons.Add(Input.GetKey(KeyCode.Alpha4));
            inputRequest.Buttons.Add(Input.GetKey(KeyCode.Alpha5));
            inputRequest.Buttons.Add(Input.GetKey(KeyCode.Alpha6));
            inputRequest.Buttons.Add(Input.GetKey(KeyCode.Alpha7));
            inputRequest.Buttons.Add(Input.GetKey(KeyCode.Alpha8));
            inputRequest.Buttons.Add(Input.GetKey(KeyCode.Alpha9));
            inputRequest.Buttons.Add(Input.GetKey(KeyCode.Alpha0));

            client.SendInput(inputRequest);
        }
    }

}
