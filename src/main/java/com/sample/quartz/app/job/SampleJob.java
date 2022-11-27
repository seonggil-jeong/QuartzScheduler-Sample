package com.sample.quartz.app.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * an interface to be implemented by components that you wish to have executed by the scheduler
 */
@Slf4j
@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class SampleJob implements Job {


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        log.info("userId : " + jobDataMap.get("userId"));
        log.info("content : " + jobDataMap.get("content"));

        log.info("[send Message] " + jobDataMap.get("content") + " to " + jobDataMap.get("userId"));

    }
}
