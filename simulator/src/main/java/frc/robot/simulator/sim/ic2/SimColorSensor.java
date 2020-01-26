package frc.robot.simulator.sim.ic2;

public class SimColorSensor {
    public static final byte partId = (byte) 0xC2;
    public enum Register {
        kMainCtrl(0x00),
        kProximitySensorLED(0x01),
        kProximitySensorPulses(0x02),
        kProximitySensorRate(0x03),
        kLightSensorMeasurementRate(0x04),
        kLightSensorGain(0x05),
        kPartID(0x06),
        kMainStatus(0x07),
        kProximityData(0x08),
        kDataInfrared(0x0A),
        kDataGreen(0x0D),
        kDataBlue(0x10),
        kDataRed(0x13);

        public final byte bVal;
        Register(int i) { this.bVal = (byte) i; }
    }

    private final int port;
    public byte red = (byte) 0x00;
    public byte green = (byte) 0xFF;
    public byte blue = (byte) 0x00;

    public SimColorSensor(int port) {
        this.port = port;
    }
}
