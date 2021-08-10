package com.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.batch.model.Feedback;
import com.batch.processors.FeedbackProcessor;
import com.batch.repository.FeedbackRepository;

@Configuration
@EnableBatchProcessing
public class FeedbackJobConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Value("classPath:/data/feedback-data.csv")
	private Resource resource;
	
	@Bean
    public Job feedbackJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(feedbackStep())
                .build();
    }
	
	@Bean
    public Step feedbackStep() {
        return stepBuilderFactory
                .get("step")
                .<Feedback, Feedback>chunk(10)
                .reader(feedbackReader())
                .processor(feedbackProcessor())
                .writer(feedbackWriter())
                .build();
    }
	
	@Bean
    public ItemProcessor<Feedback, Feedback> feedbackProcessor() {
        return new FeedbackProcessor();
    }
	
	@Bean
    public FlatFileItemReader<Feedback> feedbackReader() {
        FlatFileItemReader<Feedback> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(feedbackLineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(resource);
        return itemReader;
    }
	
	@Bean
    public LineMapper<Feedback> feedbackLineMapper() {
        DefaultLineMapper<Feedback> lineMapper = new DefaultLineMapper<Feedback>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "rating" });
        lineTokenizer.setIncludedFields(new int[] { 0 });
        BeanWrapperFieldSetMapper<Feedback> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Feedback.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
	
	@Bean
    public RepositoryItemWriter<Feedback> feedbackWriter() {
		RepositoryItemWriter<Feedback> writer = new RepositoryItemWriter<>();
        writer.setRepository(feedbackRepository);
        writer.setMethodName("save");
        return writer;
    }
}
