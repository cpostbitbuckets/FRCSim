package frc.robot.simulator.sim.config.serialization;

import org.yaml.snakeyaml.introspector.BeanAccess;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Use this to sort our yaml so the motor config looks ok
 */
public class OrderedPropertyUtils extends PropertyUtils {

    private static final List<String> sortedKeys = List.of(
            "hideFollowers",
            "motors",
            "id",
            "name",
            "model",
            "kt",
            "kv",
            "resistance",
            "inertia",
            "gearRatio"
    );


    @Override
    protected Set<Property> createPropertySet(Class<?> type, BeanAccess bAccess) {
        Set<Property> sortedProperties = new LinkedHashSet<>();
        Map<String, Property> propertiesMap = getPropertiesMap(type, bAccess);
        for (String key : sortedKeys) {
            Property prop = propertiesMap.get(key);
            if (prop != null && prop.isWritable()) {
                sortedProperties.add(prop);
            }
        }

        // add all the other properties to the list
        for (Property property : propertiesMap.values()) {
            if (property.isWritable() && !sortedProperties.contains(property)) {
                sortedProperties.add(property);
            }
        }
        return sortedProperties;
    }
}
