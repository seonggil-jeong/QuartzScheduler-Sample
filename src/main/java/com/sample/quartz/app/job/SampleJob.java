package com.sample.quartz.app.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 *  an interface to be implemented by components that you wish to have executed by the scheduler
 */
@Slf4j
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SampleJob implements Job {


    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        System.out.println(this.getClass().getName() + "Sample Job Start! [ " + LocalDate.now() + " ]");


    }
}
