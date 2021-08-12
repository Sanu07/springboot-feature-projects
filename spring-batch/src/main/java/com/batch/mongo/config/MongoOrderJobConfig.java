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

import com.batch.mapper.OrderPostgresMapper;
import com.batch.mongo.model.Order;
import com.batch.mongo.processors.OrderProcessor;
import com.batch.repository.OrderRepository;

@Configuration
@EnableBatchProcessing
public class MongoOrderJobConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Bean(name = "orderMongoJob")
    public Job orderMongoJob(JdbcCursorItemReader<com.batch.model.Order> itemReader, MongoItemWriter<Order> itemWriter) throws Exception {
        return jobBuilderFactory
                .get("orderMongoJob")
                .incrementer(new RunIdIncrementer())
                .start(orderMongoStep(itemReader, itemWriter))
                .build();
    }
	
	@Bean
    public Step orderMongoStep(JdbcCursorItemReader<com.batch.model.Order> itemReader, MongoItemWriter<Order> itemWriter)
            throws Exception {

        return this.stepBuilderFactory.get("orderMongoStep").<com.batch.model.Order, Order>chunk(10).reader(itemReader)
                .processor(orderMongoProcessor()).writer(itemWriter).build();
    }
	
	@Bean
    public JdbcCursorItemReader<com.batch.model.Order> orderMongoReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<com.batch.model.Order>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql("SELECT * FROM ORDER_DETAILS")
                .rowMapper(new OrderPostgresMapper())
                .build();
    }
	
	@Bean
    public ItemProcessor<com.batch.model.Order, Order> orderMongoProcessor() {
        return new OrderProcessor();
    }
	
	@Bean
    public MongoItemWriter<Order> orderMongoWriter() {
        return new MongoItemWriterBuilder<Order>().template(mongoTemplate).collection("order_details")
                .build();
    }
}
