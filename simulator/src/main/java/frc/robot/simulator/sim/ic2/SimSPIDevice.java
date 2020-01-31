package frc.robot.simulator.sim.ic2;

public class SimSPIDevice {
    protected final int port;
    public int updateRateHz = 60;
    boolean msbFirst;
    boolean sampleOnTrailing;
    boolean clkIdleHigh;
    boolean chipSelectActiveLow;

    public SimSPIDevice(int port) {
        this.port = port;
    }

    public void setOps(boolean msbFirst, boolean sampleOnTrailing, boolean clkIdleHigh) {
        this.msbFirst = msbFirst;
        this.sampleOnTrailing = sampleOnTrailing;
        this.clkIdleHigh = clkIdleHigh;
    }

    public void setChipSelectActiveLow(boolean chipSelectActiveLow) {
        this.chipSelectActiveLow = chipSelectActiveLow;
    }
}
