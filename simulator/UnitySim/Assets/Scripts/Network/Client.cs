using Grpc.Core;
using FRCSim;
using System.Threading;
using System.Threading.Tasks;
using Google.Protobuf.WellKnownTypes;
using System;
using UnityEngine;

public class Client : MonoBehaviour
{
    private const int LOOP_DELAY = 50; // ms

    private const int ERROR_DELAY = 100; // ms

    private const string javaServer = "localhost:50051";

    private InputRequest lastInputRequest = new InputRequest();
    private InputRequest NextInputRequest { get; set; } = null;

    private MotorOutputs lastMotorOuputs = new MotorOutputs();
    private MotorOutputs NextMotorOutputs { get; set; } = null;

    private CancellationTokenSource tokenSource = new CancellationTokenSource();

    public bool Connected { get; private set; } = false;
    private bool isConnectionOpen = false;

    internal void SendInput(InputRequest inputRequest)
    {
        NextInputRequest = inputRequest;
    }

    internal void SendUpdatedMotorOutputs(MotorOutputs outputs)
    {
        NextMotorOutputs = outputs;
    }

    void OnEnable()
    {
        OpenConnection();
    }

    /// <summary>
    /// Close any connections if the client is disabled.
    /// </summary>
    void OnDisable()
    {
        Debug.Log("Disabling Client");
        CloseConnection();
    }

    /// <summary>
    /// Attempt to open the connection to HEL
    /// </summary>
    public void OpenConnection()
    {
        if (Connected)
        {
            isConnectionOpen = false; // Kill threads so we can restart them
            Thread.Sleep(100); // ms
        }
        if (!Connected)
        {
            isConnectionOpen = true;
            Task.Factory.StartNew(SendData, tokenSource.Token);
        }
    }

    private async void SendData()
    {
        var channel = new Channel(javaServer, ChannelCredentials.Insecure);

        var conn = new global::FRCSim.RobotService.RobotServiceClient((global::Grpc.Core.ChannelBase)channel);
        while (isConnectionOpen) // Run while robot code is running or until the object stops existing
        {
            try
            {
                Debug.Log("Connecting to robot.");
                await conn.ConnectToRobotAsync(new Empty(), new CallOptions().WithWaitForReady());
                Debug.Log("Connected to robot, waiting for streams to open");

                using (var inputCall = conn.Input(null, null, tokenSource.Token))
                {
                    using (var outputsCall = conn.UpdateMotorOutputs(null, null, tokenSource.Token))
                    {
                        while (isConnectionOpen)
                        {
                            if (NextInputRequest != null)
                            {
                                if (!NextInputRequest.Equals(lastInputRequest))
                                {
                                    lastInputRequest = NextInputRequest;
                                    NextInputRequest = null;
                                    await inputCall.RequestStream.WriteAsync(lastInputRequest);
                                    // Debug.Log("Sent input to robot");
                                }
                                NextInputRequest = null;
                            }

                            if (NextMotorOutputs != null)
                            {
                                // Debug.Log("SendData has new motor outputs");
                                if (!NextMotorOutputs.Equals(lastMotorOuputs))
                                {
                                    lastMotorOuputs = NextMotorOutputs;
                                    NextMotorOutputs = null;
                                    // Debug.Log("Sending motor outputs to robot");
                                    await outputsCall.RequestStream.WriteAsync(lastMotorOuputs);
                                    // Debug.Log("Sent motor outputs to robot");
                                }
                                else
                                {
                                    // Debug.Log("SendData didn't send duplicate motor outputs");

                                }
                                NextMotorOutputs = null;
                            }

                            Connected = true;
                            await Task.Delay(LOOP_DELAY); // ms
                        }
                    }

                }
            }
            catch (Exception e)
            {
                Debug.Log("Lost connection: " + e);
                Connected = false;
                await Task.Delay(ERROR_DELAY); // ms
            }
        }
        using (var call = conn.Input())
        {
            await call.RequestStream.CompleteAsync();
        }
        using (var call = conn.UpdateMotorOutputs())
        {
            await call.RequestStream.CompleteAsync();
        }

        Debug.Log("Closed connection");

        Connected = false;
        isConnectionOpen = false;
    }


    public void CloseConnection()
    {
        Debug.Log("Cancelling all client threads and closing connection");
        tokenSource.Cancel();

        isConnectionOpen = false;
    }

}
