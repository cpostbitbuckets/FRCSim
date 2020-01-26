using UnityEngine;
using Grpc.Core;
using System.Threading.Tasks;
using FRCSim;
using Google.Protobuf.WellKnownTypes;
using System.Collections.Generic;
using System;
using System.Threading;
using UnityEngine.EventSystems;

/// <summary>
/// The server side implementation of the RobotService
/// </summary>
public class RobotService : FRCSim.RobotService.RobotServiceBase
{
    // create an empty response for use later
    private static Empty emptyResponse = new Empty();

    private CancellationTokenSource cancellationTokenSource = new CancellationTokenSource();

    public void Stop()
    {
        Debug.Log("Stopping server, cancelling streams.");
        // cancel any pending streams
        cancellationTokenSource.Cancel();
    }

    public override Task<Empty> UpdateMotor(MotorConfig request, ServerCallContext context)
    {
        Debug.Log("Updating motor " + request.Id + " " + request.ToString());
        EventManager.PublishMotorConfigUpdateEvent(request);
        return Task.FromResult(emptyResponse);
    }

    public override Task<Empty> Robot(RobotState request, ServerCallContext context)
    {
        Debug.Log("Updating robot state " + request.ToString());
        EventManager.PublishRobotStateUpdateEvent(request);
        return Task.FromResult(emptyResponse);
    }

}
