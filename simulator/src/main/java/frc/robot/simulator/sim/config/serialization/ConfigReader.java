package frc.robot.simulator.sim.config.serialization;

import com.google.inject.Inject;
import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.lib.filter.Filter;
import frc.robot.simulator.sim.SimulatorSettings;
import frc.robot.simulator.sim.config.SimulatorConfig;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class ConfigReader {

    private final SimulatorSettings simulatorSettings;

    @Inject
    public ConfigReader(SimulatorSettings simulatorSettings) {
        this.simulatorSettings = simulatorSettings;
    }

    public SimulatorConfig loadConfig() throws IOException {
        SimulatorConfig simulatorConfig;
        final File configFile = new File(simulatorSettings.getConfigFile());
        if (configFile.exists()) {
            simulatorConfig = ConfigReader.readConfigFile(configFile);
            if (simulatorConfig == null) {
                throw new RuntimeException("Failed to read simulator config from " + configFile.getAbsolutePath());
            }
            simulatorConfig.init();
        } else {
            simulatorConfig = new SimulatorConfig();
        }

        return simulatorConfig;
    }

    /**
     * Read config from resources
     */
    public static SimulatorConfig readConfig(String template) throws IOException {
        Jinjava jinjava = new Jinjava();

        // add a new "int" filter to convert any number to an int
        jinjava.getGlobalContext().registerFilter(new Filter() {

            @Override
            public String getName() {
                return "int";
            }

            @Override
            public Object filter(Object var, JinjavaInterpreter interpreter, String... args) {
                return Double.valueOf(var.toString()).intValue();
            }

        });

        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);

        Yaml yaml = new Yaml(representer);

        // parse the yaml file as a Map, so we can call "render" on it and render any
        // jinja2 template stuff
        Map<String, Object> variables = yaml.load(template);
        String renderedTemplate = jinjava.render(template, variables);

        // Now load the config in as a Config object, from the rendered template with
        // expanded variables and calculations
        SimulatorConfig config = yaml.loadAs(renderedTemplate, SimulatorConfig.class);

        return config;
    }

    public static SimulatorConfig readConfigFile(File file) throws IOException {
        return readConfig(FileUtils.readFileToString(file, Charset.defaultCharset()));
    }

}
