package com.sample.quartz.app.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.springframework.stereotype.Component;

/**
 * Job 실행 전후에 Event 실행
 */
@Slf4j
public class JobsListener implements JobListener {

    @Override
    public String getName() {
        return "JobsListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("Before Start Job");
        JobKey jobKey = context.getJobDetail().getKey();


        log.info("jobKey : {}", jobKey);

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("Operation aborted");
        JobKey jobKey = context.getJobDetail().getKey();


        log.info("jobKey : {}", jobKey);

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("Job Was Executed");
        JobKey jobKey = context.getJobDetail().getKey();


        log.info("jobKey : {}", jobKey);

    }
}
