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

import com.batch.model.Order;
import com.batch.processors.OrderProcessor;
import com.batch.repository.OrderRepository;

@Configuration
@EnableBatchProcessing
public class OrderJobConfig {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Value("classPath:/data/order-data.csv")
	private Resource resource;
	
	@Bean
    public Job orderJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(orderStep())
                .build();
    }
	
	@Bean
    public Step orderStep() {
        return stepBuilderFactory
                .get("step")
                .<Order, Order>chunk(10)
                .reader(orderReader())
                .processor(orderProcessor())
                .writer(orderWriter())
                .build();
    }
	
	@Bean
    public ItemProcessor<Order, Order> orderProcessor() {
        return new OrderProcessor();
    }
	
	@Bean
    public FlatFileItemReader<Order> orderReader() {
        FlatFileItemReader<Order> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(orderLineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(resource);
        return itemReader;
    }
	
	@Bean
    public LineMapper<Order> orderLineMapper() {
        DefaultLineMapper<Order> lineMapper = new DefaultLineMapper<Order>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "totalAmount" });
        lineTokenizer.setIncludedFields(new int[] { 0 });
        BeanWrapperFieldSetMapper<Order> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Order.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
	
	@Bean
    public RepositoryItemWriter<Order> orderWriter() {
		RepositoryItemWriter<Order> writer = new RepositoryItemWriter<>();
        writer.setRepository(orderRepository);
        writer.setMethodName("save");
        return writer;
    }
}
