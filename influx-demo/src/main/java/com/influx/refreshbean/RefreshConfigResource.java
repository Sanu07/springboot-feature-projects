package com.influx.refreshbean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("refresh")
public class RefreshConfigResource {

    @Autowired
    private ConfigReloaderService reloaderService;

    @Autowired
    private PropertyUpdater propertyUpdater;

    @Autowired
    private RefreshScope refreshScope;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    private ConfigurableApplicationContext context;

    @PostMapping("{interval}")
    public void refresh(@PathVariable int interval) {
        // reloaderService.refresh(interval);
        SchedulerService bean1 = context.getBean(SchedulerService.class);
        propertyUpdater.updateProperty();
        refreshScope.refresh(SchedulerService.class);
        if (interval == 1000) {
            SchedulerService bean2 = context.getBean(SchedulerService.class);
            schedulerService.scheduler();
        }
    }
}
