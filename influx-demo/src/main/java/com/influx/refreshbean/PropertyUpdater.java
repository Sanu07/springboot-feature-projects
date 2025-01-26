package com.influx.refreshbean;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

@Component
public class PropertyUpdater {

    private final ConfigurableApplicationContext context;

    // Inject the application context to access ConfigurableEnvironment
    public PropertyUpdater(ConfigurableApplicationContext context) {
        this.context = context;
    }

    // Method to dynamically change the property value
    public void updateProperty() {
        ConfigurableEnvironment environment = context.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();

        // Dynamically change the property value
        propertySources.addFirst(new org.springframework.core.env.MapPropertySource("dynamic-properties", 
            java.util.Collections.singletonMap("app.influx.interval", "2000")));
        
        // You can also log or access the updated value
        String updatedValue = environment.getProperty("app.influx.interval");
        System.out.println("Updated Property Value: " + updatedValue);
    }
}