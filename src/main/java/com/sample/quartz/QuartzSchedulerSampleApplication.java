package com.sample.quartz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@SpringBootApplication
@EnableScheduling
public class QuartzSchedulerSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuartzSchedulerSampleApplication.class, args);
    }


}
