package com.training.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.training.batch.CustomerItemProcessor;
import com.training.batch.JobCompletionNotificationListener;
import com.training.bean.CustomerRequests;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	private Logger log=LoggerFactory.getLogger(BatchConfiguration.class);
  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  @Bean
  public FlatFileItemReader<CustomerRequests> reader() {
	  log.info("Reading the csv file..");
    return new FlatFileItemReaderBuilder<CustomerRequests>()
      .name("customerItemReader")
      .resource(new ClassPathResource("sample-data.csv"))
      .delimited()
      .names(new String[]{"email_id","name", "address","phoneno"})
      .fieldSetMapper(new BeanWrapperFieldSetMapper<CustomerRequests>() {{
        setTargetType(CustomerRequests.class);
      }})
      .build();
  }

  @Bean
  public CustomerItemProcessor processor() {
    return new CustomerItemProcessor();
  }

  @Bean
  public JdbcBatchItemWriter<CustomerRequests> writer(DataSource dataSource) {
	  log.info("writing data into db..");
    return new JdbcBatchItemWriterBuilder<CustomerRequests>()
      .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
      .sql("INSERT INTO customer_requests(email_id, name,address,phoneno) VALUES (:email_id, :name,:address,:phoneno)")
      .dataSource(dataSource)
      .build();
  }

  @Bean
  public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
	  log.info("here");
    return jobBuilderFactory.get("importUserJob")
      .incrementer(new RunIdIncrementer())
      .listener(listener)
      .flow(step1)
      .end()
      .build();
  }

  @Bean
  public Step step1(JdbcBatchItemWriter<CustomerRequests> writer) {
	  
	  log.info("Step Builder");
    return stepBuilderFactory.get("step1")
      .<CustomerRequests, CustomerRequests> chunk(1)
      .reader(reader())
      .processor(processor())
      .writer(writer)
      .build();
  }
}