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

import com.batch.mapper.CustomerPostgresMapper;
import com.batch.mongo.model.Customer;
import com.batch.mongo.processors.CustomerProcessor;
import com.batch.repository.CustomerRepository;

@Configuration
@EnableBatchProcessing
public class MongoCustomerJobConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Bean(name = "customerMongoJob")
    public Job customerMongoJob(JdbcCursorItemReader<com.batch.model.Customer> itemReader, MongoItemWriter<Customer> itemWriter) throws Exception {
        return jobBuilderFactory
                .get("customerMongoJob")
                .incrementer(new RunIdIncrementer())
                .start(customerMongoStep(itemReader, itemWriter))
                .build();
    }
	
	@Bean
    public Step customerMongoStep(JdbcCursorItemReader<com.batch.model.Customer> itemReader, MongoItemWriter<Customer> itemWriter)
            throws Exception {

        return this.stepBuilderFactory.get("customerMongoStep").<com.batch.model.Customer, Customer>chunk(10).reader(itemReader)
                .processor(customerMongoProcessor()).writer(itemWriter).build();
    }
	
	@Bean
    public JdbcCursorItemReader<com.batch.model.Customer> customerMongoReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<com.batch.model.Customer>()
                .name("cursorItemReader")
                .dataSource(dataSource)
                .sql("SELECT * FROM CUSTOMER_DETAILS")
                .rowMapper(new CustomerPostgresMapper())
                .build();
    }
	
	@Bean
    public ItemProcessor<com.batch.model.Customer, Customer> customerMongoProcessor() {
        return new CustomerProcessor();
    }
	
	@Bean
    public MongoItemWriter<Customer> customerMongoWriter() {
        return new MongoItemWriterBuilder<Customer>().template(mongoTemplate).collection("customer_details")
                .build();
    }
}
