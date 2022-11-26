package com.sample.quartz;

import com.sample.quartz.app.job.vo.CronJobRequest;
import com.sample.quartz.app.job.vo.SampleJobRequest;
import com.sample.quartz.app.listener.JobsListener;
import com.sample.quartz.app.listener.TriggersListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Managing Trigger and Job Creation
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class QuartzHandler {
    private final Scheduler scheduler;

    @PostConstruct
    public void init() {
        try {
            // Initialization (DB, )
            scheduler.clear();
            scheduler.getListenerManager()
                    .addJobListener(new JobsListener());
            scheduler.getListenerManager()
                    .addTriggerListener(new TriggersListener());
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("executeCount", 1);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public <T extends Job> void addJob(Class<? extends Job> job, SampleJobRequest request)
            throws SchedulerException {

        JobDetail jobDetail = buildJobDetail(job, request.getJobName(), request.getJobDescription());
        Trigger trigger = buildTrigger(request);

        if (scheduler.checkExists(jobDetail.getKey())) {
            scheduler.deleteJob(jobDetail.getKey());
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    public <T extends Job> void addCronJob(Class<? extends Job> job, CronJobRequest request)
            throws SchedulerException {

        JobDetail jobDetail = buildJobDetail(job, request.getJobName(), request.getJobDescription());
        Trigger trigger = buildCronTrigger(request.getCronExpression());

        if (scheduler.checkExists(jobDetail.getKey())) {
            scheduler.deleteJob(jobDetail.getKey());
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }


    public <T extends Job> JobDetail buildJobDetail(Class<? extends Job> job, final String jobName, String jobDescription) {

        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("executeCount", 1);

        return JobBuilder.newJob(job)
                .withIdentity(jobName)
                .withDescription(jobDescription)
                .usingJobData(jobDataMap)
                .build();
    }

    private Trigger buildTrigger(final SampleJobRequest request) {
        SimpleTriggerFactoryBean triggerFactory = new SimpleTriggerFactoryBean();

        triggerFactory.setName(request.getJobName());
        triggerFactory.setGroup(request.getJobGroup());
        triggerFactory.setStartTime(localTimeToDate(request.getStartTime()));
        triggerFactory.setRepeatCount(0);
        triggerFactory.setRepeatInterval(0);

        triggerFactory.afterPropertiesSet();
        return triggerFactory.getObject();

    }

    private Trigger buildCronTrigger(String cronExp) {
        CronTriggerFactoryBean cronTriggerFactory = new CronTriggerFactoryBean();
        cronTriggerFactory.setCronExpression(cronExp);
        cronTriggerFactory.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_FIRE_NOW);
        try {
            cronTriggerFactory.afterPropertiesSet();
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return cronTriggerFactory.getObject();

    }


    private Date localTimeToDate(final LocalTime startTime) {
        Instant instant = startTime.atDate(LocalDate
                        .of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()))
                .atZone(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);
    }
}
