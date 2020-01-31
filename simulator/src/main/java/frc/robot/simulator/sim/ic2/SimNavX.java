package frc.robot.simulator.sim.ic2;

/**
 * A simulated navx device
 */
public class SimNavX extends SimSPIDevice {

    public float heading;
    public SimNavX(int port) {
        super(port);
    }

}
