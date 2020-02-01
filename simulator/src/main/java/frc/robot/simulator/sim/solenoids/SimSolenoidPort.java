package frc.robot.simulator.sim.solenoids;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class SimSolenoidPort {
    public int handle;
    public String name;
    public int module;
    public int channel;
    public DoubleSolenoid.Value state = DoubleSolenoid.Value.kOff;

    public SimSolenoidPort(int handle, String name, int module, int channel) {
        this.name = name;
        this.handle = handle;
        this.module = module;
        this.channel = channel;
    }
}
