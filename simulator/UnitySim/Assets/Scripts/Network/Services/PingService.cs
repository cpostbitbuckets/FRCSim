using UnityEngine;
using Grpc.Core;
using System.Threading.Tasks;
using System;
using FRCSim;
using Google.Protobuf.WellKnownTypes;

class PingService : FRCSim.PingService.PingServiceBase
{
    // Server side handler of the Ping RPC
    public override Task<PongResponse> Ping(PingMessage message, ServerCallContext context)
    {
        Debug.Log("Pinged!");
        return Task.FromResult(new PongResponse());
    }
}
