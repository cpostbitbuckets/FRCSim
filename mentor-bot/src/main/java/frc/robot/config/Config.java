package frc.robot.config;

public class Config implements SimSupportingConfig {

    // all motors use the same slot id for motion magic vs velocity.
    public int positionSlotIndex = 0;
    public int velocitySlotIndex = 1;
    public int pidIndex = 0;

    public TalonDriveConfig talonDriveConfig = new TalonDriveConfig();
    public SparkDriveConfig sparkDriveConfig = new SparkDriveConfig();
    public ArmConfig armConfig = new ArmConfig();
    public OI oi = new OI();

    // timeout for doing various talon config operations
    public int ctreTimeout = 30;

    public Config() {
        // TODO: make this a param or config file or something...
        overrideForSim();
    }

    @Override
    public void overrideForSim() {
        talonDriveConfig.overrideForSim();
        sparkDriveConfig.overrideForSim();
        armConfig.overrideForSim();
        oi.overrideForSim();
    }

}
