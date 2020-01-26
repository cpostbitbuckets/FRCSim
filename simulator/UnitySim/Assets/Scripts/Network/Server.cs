using UnityEngine;
using Grpc.Core;
using System.Threading.Tasks;
using System;
using FRCSim;
using UnityEngine.EventSystems;

public class Server : MonoBehaviour
{
    private Grpc.Core.Server server;

    public void OnEnable()
    {
        // unity runs on port 50052
        // java runs on 50051
        const int port = 50052;

        server = new Grpc.Core.Server
        {
            Services = {
                FRCSim.RobotService.BindService(new RobotService()),
                FRCSim.PingService.BindService(new PingService())
            },
            Ports = { new ServerPort("localhost", port, ServerCredentials.Insecure) },
        };

        Debug.Log("Starting server on port: " + port);
        server.Start();
        Debug.Log("Started server on port: " + port);
    }

    public void OnDisable()
    {
        Debug.Log("Stopping server");
        // shutdown the server
        try {
            server.ShutdownAsync().Wait(1000);
            Debug.Log("Server is stopped.");
        } catch (Exception e) {
            Debug.Log("Server did not shutdown in 1s, killing it." + e);
            server.KillAsync().Wait();
        }
    }

}
