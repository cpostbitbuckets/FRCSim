using System.Collections;
using System.Collections.Generic;
using FRCSim;

public class SimMotor
{
    private int baseArbId;

    public SimMotor()
    {
        // create some empty pid slots
        Config.Fpid.Add(new FRCSim.MotorConfig.Types.FPID());
        Config.Fpid.Add(new FRCSim.MotorConfig.Types.FPID());
        Config.Fpid.Add(new FRCSim.MotorConfig.Types.FPID());
        Config.Fpid.Add(new FRCSim.MotorConfig.Types.FPID());
    }

    public SimMotor(int handle) : this()
    {
        // Talons do some funkiness with the ID vs what we will use as the handle
        this.Id = (int) (handle & (~0x02040000));
        this.Handle = handle;

        Config.Id = Id;
        Config.Handle = Handle;
    }

    public int Id { get; set; }

    public long Handle { get; set; }

    public MotorConfig Config { get; set; } = new MotorConfig();

    public SimMotor Following { get; set; } = null;

    // the output of the motor, between -1.0 and 1.0
    public double Output { get; set; } = 0;
    public double Velocity { get; set; } = 0;
    public double Position { get; set; } = 0;

    public double IntegralState { get; set; } = 0;
    public double LastError { get; set; } = 0;
    public double SensorPosition { get; internal set; }
}
