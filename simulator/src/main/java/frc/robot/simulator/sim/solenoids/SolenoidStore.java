package frc.robot.simulator.sim.solenoids;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import frc.robot.simulator.sim.config.SimulatorConfig;
import frc.robot.simulator.sim.events.EventManager;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class SolenoidStore {

    /**
     * The SimulatorConfig is injected
     */
    private final SimulatorConfig simulatorConfig;

    private static int nextHandle = 10000;

    private static Map<Integer, SimSolenoidPort> solenoidPortsByHandle = new HashMap<>();

    @Inject
    public SolenoidStore(SimulatorConfig simulatorConfig) {
        this.simulatorConfig = simulatorConfig;
    }

    /**
     * Create a new Solenoid port, returning the handle
     * @param module
     * @param channel
     * @return
     */
    public int createSolenoidPort(int module, int channel) {
        SimulatorConfig.Solenoid solenoidConfig = simulatorConfig.getConfigForSolenoid(module);
        if (solenoidConfig == null) {
            solenoidConfig = simulatorConfig.addNewDefaultSolenoid(module);
        }

        SimSolenoidPort simSolenoidPort = new SimSolenoidPort(nextHandle++, solenoidConfig.name, module, channel);
        solenoidPortsByHandle.put(simSolenoidPort.handle, simSolenoidPort);
        EventManager.publish(simSolenoidPort);
        return simSolenoidPort.handle;
    }


    public SimSolenoidPort get(int halPortHandle) {
        return solenoidPortsByHandle.get(halPortHandle);
    }

    public List<SimSolenoidPort> getSimSolenoidPortsSorted() {
        return solenoidPortsByHandle.values().stream().sorted(Comparator.comparingInt(s -> s.module)).collect(Collectors.toList());
    }
}
