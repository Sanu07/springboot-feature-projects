package com.batch.mongo.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.batch.mapper.FeedbackPostgresMapper;
import com.batch.mongo.model.Feedback;
import com.batch.mongo.processors.FeedbackProcessor;
import com.batch.repository.FeedbackRepository;
import com.batch.repository.FeedbackRepository;

@Configuration
@EnableBatchProcessing
public class MongoFeedbackJobConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private FeedbackRepository feedbackRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Bean(name = "feedbackMongoJob")
    public Job feedbackMongoJob(JdbcCursorItemReader<com.batch.model.Feedback> itemReader, MongoItemWriter<Feedback> itemWriter) throws Exception {
        return jobBuilderFactory
                .get("feedbackMongoJob")
                .incrementer(new RunIdIncrementer())
                .start(feedbackMongoStep(itemReader, itemWriter))
                .build();
    }
	
	@Bean
    public Step feedbackMongoStep(JdbcCursorItemReader<com.batch.model.Feedback> itemReader, MongoItemWriter<Feedback> itemWriter)
            throws Exception {

        return this.stepBuilderFactory.get("feedbackMongoStep").<com.batch.model.Feedback, Feedback>chunk(10).reader(itemReader)
                .processor(feedbackMongoProcessor()).writer(itemWriter).build();
    }
	
	@Bean
    public JdbcCursorItemReader<com.batch.model.Feedback> feedbackMongoReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<com.batch.model.Feedback>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql("SELECT * FROM FEEDBACK_DETAILS")
                .rowMapper(new FeedbackPostgresMapper())
                .build();
    }
	
	@Bean
    public ItemProcessor<com.batch.model.Feedback, Feedback> feedbackMongoProcessor() {
        return new FeedbackProcessor();
    }
	
	@Bean
    public MongoItemWriter<Feedback> feedbackMongoWriter() {
        return new MongoItemWriterBuilder<Feedback>().template(mongoTemplate).collection("feedback_details")
                .build();
    }
}
