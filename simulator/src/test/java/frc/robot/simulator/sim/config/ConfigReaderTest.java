package frc.robot.simulator.sim.config;

import frc.robot.simulator.sim.config.serialization.ConfigReader;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigReaderTest {

    @Test
    public void readConfigEmpty() throws Exception {
        SimulatorConfig config = ConfigReader.readConfig("hideFollowers: true\ntransmissions: []\n");
        assertEquals(true, config.hideFollowers);
        assertEquals(0, config.transmissions.size());
    }

    @Test
    public void readConfigMotors() throws Exception {
        SimulatorConfig config = ConfigReader.readConfig(
                "hideFollowers: true\n" +
                "transmissions:\n" +
                "- motors:\n" +
                "  - id: 1\n" +
                "    name: Left Drive Motor\n" +
                "    model: 775pro\n" +
                "  gearRatio: 8.962962962962964\n");
        assertEquals(true, config.hideFollowers);
        assertEquals(1, config.transmissions.size());
        assertEquals(1, config.transmissions.get(0).motors.size());
        assertEquals("Left Drive Motor", config.transmissions.get(0).motors.get(0).name);
    }

    @Test
    public void readConfigTemplate() throws Exception {
        SimulatorConfig config = ConfigReader.readConfig(
                "hideFollowers: true\n" +
                        "transmissions:\n" +
                        "- motors:\n" +
                        "  - id: 1\n" +
                        "    name: Left Drive Motor\n" +
                        "    model: 775pro\n" +
                        "  gearRatio: {{ 242.0/27.0 }}\n");
        assertEquals(true, config.hideFollowers);
        assertEquals(1, config.transmissions.size());
        assertEquals(1, config.transmissions.get(0).motors.size());
        assertEquals("Left Drive Motor", config.transmissions.get(0).motors.get(0).name);
    }

}