package frc.robot.config.settings;

public class FPID {
    public double f = 0;
    public double p = 0;
    public double i = 0;
    public double d = 0;
    public double iZone = 200;

    public FPID() {
    }

    public FPID(double f, double p, double i, double d) {
        this.f = f;
        this.p = p;
        this.i = i;
        this.d = d;
    }

    public FPID(double f, double p, double i, double d, double iZone) {
        this.f = f;
        this.p = p;
        this.i = i;
        this.d = d;
        this.iZone = iZone;
    }

    /**
     * Simple method to copy values from one FPID to another
     * Used by the builder
     * @param fpid
     */
    public void set(FPID fpid) {
        this.f = fpid.f;
        this.p = fpid.p;
        this.i = fpid.i;
        this.d = fpid.d;
        this.iZone = fpid.iZone;
    }

    public void set(double f, double p, double i, double d) {
        this.f = f;
        this.p = p;
        this.i = i;
        this.d = d;
    }

}
