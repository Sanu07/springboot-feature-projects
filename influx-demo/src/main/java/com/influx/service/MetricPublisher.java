package com.influx.service;

import com.influx.model.Employee;
import com.influx.model.Metric;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import io.micrometer.influx.InfluxMeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class MetricPublisher {

    @Autowired
    private InfluxMeterRegistry meterRegistry;
    private Counter counter;

    public Counter getCounter(Metric metric) {
//        Counter.Builder builder = Counter.builder(metric.getName());
//        for (Map.Entry<String, String> tag: metric.getTags().entrySet()) {
//            builder.tag(tag.getKey(), tag.getValue());
//        }
//        counter = builder.register(meterRegistry);
        return counter;
    }


    public void publish(Employee employee) {
//        Counter counter = Metrics.counter("employee.requests",
//                "employeeId",
//                employee.getId().toString(),
//                "name",
//                employee.getName(),
//                "status",
//                employee.getStatus());
        if (counter == null) {
            counter = getCounter(Metric.builder()
                    .name("employee.requests")
                    .tags(Map.of("status", employee.getStatus(), "name", employee.getName()))
                    .build());
        }
        counter.increment();
        log.info("counter with Id: {} value: {}", counter.getId(), counter.count());
    }


    @PostConstruct
    public void postConstruct() {
        Metrics.addRegistry(meterRegistry);
        // this.counter = Counter.builder("employee.requests").register(meterRegistry);
    }
}
