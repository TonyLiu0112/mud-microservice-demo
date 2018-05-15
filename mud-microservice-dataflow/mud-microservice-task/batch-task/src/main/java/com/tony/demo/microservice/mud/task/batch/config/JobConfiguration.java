package com.tony.demo.microservice.mud.task.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class JobConfiguration {

    private final Logger logger = LoggerFactory.getLogger(JobConfiguration.class);

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public JobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job logJob() {
        return jobBuilderFactory.get("logJob").start(
                stepBuilderFactory
                        .get("logJob-step1")
                        .tasklet((stepContribution, chunkContext) -> {
                            logger.info("Log Job is running!!!");
                            return RepeatStatus.FINISHED;
                        })
                        .build()
        ).build();
    }

    @Bean
    public Job timeJob() {
        return jobBuilderFactory.get("timeJob").start(
                stepBuilderFactory
                        .get("timeJob-step1")
                        .tasklet((stepContribution, chunkContext) -> {
                            logger.info("Time Job is running!!! now is {}", new Date().getTime());
                            return RepeatStatus.FINISHED;
                        })
                        .build()
        ).build();
    }

}
