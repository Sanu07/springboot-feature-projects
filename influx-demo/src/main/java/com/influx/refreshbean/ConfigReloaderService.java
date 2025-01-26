package com.influx.refreshbean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigReloaderService {

    @Autowired
    private RefreshableBeanConfig beanConfig;

    public void refresh(int interval) {
        beanConfig.setRefreshInterval(interval);
    }
}
