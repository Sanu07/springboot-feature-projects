package com.influx.refreshbean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class RefreshableBeanConfig {

    @Value("${app.influx.interval}")
    private int refreshInterval;
}
