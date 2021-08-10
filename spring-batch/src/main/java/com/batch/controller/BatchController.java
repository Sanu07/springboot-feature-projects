package com.batch.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {

	@Autowired
    private JobLauncher jobLauncher;
    
	@Autowired
    private Job customerJob;
	
	@Autowired
    private Job orderJob;
	
	@Autowired
    private Job feedbackJob;
    
	@GetMapping(path = "/populate-customers")
    public void startBatchForPopulatingCustomers() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(customerJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {

            e.printStackTrace();
        }
    }
	
	@GetMapping(path = "/populate-orders")
    public void startBatchForPopulatingOrders() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(orderJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {

            e.printStackTrace();
        }
    }
	
	@GetMapping(path = "/populate-feedbacks")
    public void startBatchForPopulatingFeedbacks() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(feedbackJob, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException
                | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {

            e.printStackTrace();
        }
    }
}
