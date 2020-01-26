package frc.robot.simulator.sim.config;

import frc.robot.simulator.sim.config.serialization.ConfigWriter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ConfigWriterTest {

    @Test
    public void dumpConfig() {
        // test empty config
        SimulatorConfig config = new SimulatorConfig();
        String output = ConfigWriter.dumpConfig(config);
        assertTrue(output.contains("hideFollowers: true"));
        assertTrue(output.contains("transmissions: []"));

        // add a motor config = new SimulatorConfig();
        SimulatorConfig.Motor motor = new SimulatorConfig.Motor();
        motor.id = 1;
        motor.name = "Left Drive Motor";
        motor.model = "775pro";

        config.transmissions.add(new SimulatorConfig.Transmission(motor));
        output = ConfigWriter.dumpConfig(config);
        assertTrue(output.contains("name: Left Drive Motor"));
    }
}
