package com.test;

import com.netflix.conductor.client.automator.TaskRunnerConfigurer;
import com.netflix.conductor.client.http.TaskClient;
import com.netflix.conductor.client.worker.Worker;
import com.test.wf.math.AddNumbersWorker;
import com.test.wf.math.InitializeWorkflow;
import com.test.wf.math.MultiplyBy2Worker;
import com.test.wf.math.MultiplyBy5Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class NetflixConductorApplication implements CommandLineRunner {

	@Autowired
	private InitializeWorkflow initializeWorkflow;

	public static void main(String[] args) {
		SpringApplication.run(NetflixConductorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		initializeWorkflow.initializeWorkflow();
	}
}
