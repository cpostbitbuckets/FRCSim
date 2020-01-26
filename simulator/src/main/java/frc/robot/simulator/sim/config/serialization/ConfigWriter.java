package frc.robot.simulator.sim.config.serialization;

import com.google.inject.Inject;
import frc.robot.simulator.sim.SimulatorSettings;
import frc.robot.simulator.sim.config.SimulatorConfig;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigWriter {

    private static final Logger log = LoggerFactory.getLogger(ConfigWriter.class);

    private final SimulatorSettings simulatorSettings;
    private final SimulatorConfig simulatorConfig;

    @Inject
    public ConfigWriter(SimulatorSettings simulatorSettings, SimulatorConfig simulatorConfig) {
        this.simulatorSettings = simulatorSettings;
        this.simulatorConfig = simulatorConfig;
    }

    /**
     * Save the config to a file, if it doesn't already exist
     */
    public void saveConfig() {
        if (!new File(simulatorSettings.getConfigFile()).exists()) {
            try {
                log.info("Writing simulator config file: " + simulatorSettings.getConfigFile());
                ConfigWriter.writeConfig(simulatorConfig, simulatorSettings.getConfigFile());
            } catch (IOException e) {
                log.error("Failed to write config file to " + simulatorSettings.getConfigFile());
            }
        }
    }

    /**
     * Dump the config to a string so it can be written to a file
     * @param config
     * @return
     */
    public static String dumpConfig(SimulatorConfig config) {
        Representer repr = new Representer();
        repr.setPropertyUtils(new OrderedPropertyUtils());

        Yaml yaml = new Yaml(repr);

        return yaml.dumpAs(config, Tag.MAP, DumperOptions.FlowStyle.BLOCK);
    }

    public static void writeConfig(SimulatorConfig config, String filename) throws IOException {
        // create the directory
        File dir = new File(FilenameUtils.getPath(filename));
        dir.mkdirs();

        // generate the yaml
        String output = dumpConfig(config);

        // output the config to a file
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(output);
        }

    }

}
