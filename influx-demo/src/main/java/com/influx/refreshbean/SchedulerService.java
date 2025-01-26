package com.influx.refreshbean;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@Service
@Slf4j
@RefreshScope
public class SchedulerService {

    @Autowired
    private RefreshableBeanConfig config;

    @Autowired
    private Environment environment;

    @Value("${app.influx.interval}")
    private Integer refreshInterval;

    @Scheduled(fixedDelayString = "${app.influx.interval}")
    public void scheduler() {
        log.info("************ Running ********** [{}]", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        Properties props = new Properties();
//        MutablePropertySources propSrcs = ((AbstractEnvironment) environment).getPropertySources();
//        StreamSupport.stream(propSrcs.spliterator(), false)
//                .filter(ps -> ps instanceof EnumerablePropertySource)
//                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
//                .flatMap(Arrays::<String>stream)
//                .forEach(propName -> log.info("Property Name -> {} :: Value->{}", propName, environment.getProperty(propName)));

        log.info("-------------- Interval -------------- {}", refreshInterval);
        log.info("Environment --> {}", environment.getProperty("app.influx.interval"));
//        MutablePropertySources propSrcs = ((AbstractEnvironment) environment).getPropertySources();
//        for (PropertySource<?> ps : propSrcs) {
//            // Step 3: Check if the property source is of type EnumerablePropertySource
//            // ps instanceof EnumerablePropertySource &&
//            if (ps instanceof OriginTrackedMapPropertySource) {
//                // Step 4: Get the property names for this EnumerablePropertySource
//                String[] propertyNames = ((EnumerablePropertySource) ps).getPropertyNames();
//
//                // Step 5: Loop over the property names
//                for (String propName : propertyNames) {
//                    // Step 6: Get the value of the property from the environment
//                    String propValue = environment.getProperty(propName);
//                    if (!propName.equals("app.influx.interval")) {
//                        continue;
//                    }
//                    // Step 7: Log the property name and value
//                    log.info("Property Name -> {} :: Value -> {}", propName, propValue);
//                }
//            }
//        }
    }

    private boolean isSchedulerInitialized = false;

    @PostConstruct
    public void init() {
        // Initialize or reinitialize scheduler after bean refresh
        reinitializeScheduler();
    }

    // Reinitialize scheduler after refresh
    private void reinitializeScheduler() {
        if (!isSchedulerInitialized) {
            log.info("Reinitializing the scheduler with updated interval: {}", refreshInterval);
            isSchedulerInitialized = true;
            // Optionally you could schedule tasks manually here, if needed
            // For example, resetting or re-scheduling the task dynamically
        }
    }
}
