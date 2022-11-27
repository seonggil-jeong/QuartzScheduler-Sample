package com.sample.quartz;

import com.sample.quartz.app.job.vo.CronJobRequest;
import com.sample.quartz.app.job.vo.JobResponse;
import com.sample.quartz.app.job.vo.SampleJobRequest;
import com.sample.quartz.app.listener.JobsListener;
import com.sample.quartz.app.listener.TriggersListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

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
            // Initialization (DB)
            scheduler.clear();
            // add Listener
            scheduler.getListenerManager()
                    .addJobListener(new JobsListener());
            scheduler.getListenerManager()
                    .addTriggerListener(new TriggersListener());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public <T extends Job> void addJob(Class<? extends Job> job, SampleJobRequest request)
            throws SchedulerException {

        Map<String, Object> params = new HashMap<>();
        params.put("content", request.getContent());
        params.put("userId", request.getUserId());

        final JobDetail jobDetail = buildJobDetail(job, request.getName(), request.getGroup(), request.getDescription(), params);
        final Trigger trigger = buildTrigger(request.getName(), request.getGroup(), request.getStartTime());

        registerJobInScheduler(jobDetail, trigger);
    }

    public <T extends Job> void addCronJob(Class<? extends Job> job, CronJobRequest request, Map params)
            throws SchedulerException {

        final JobDetail jobDetail = buildJobDetail(job, request.getName(), request.getGroup(), request.getDescription(), params);
        final Trigger trigger = buildCronTrigger(request.getCronExpression());

        registerJobInScheduler(jobDetail, trigger);
    }

    public List<JobResponse> findAllActivatedJob() {
        List<JobResponse> result = new ArrayList<>();
        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                log.info("groupName : " + groupName);

                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    List<Trigger> trigger = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

                    result.add(JobResponse.builder()
                            .jobName(jobKey.getName())
                            .groupName(jobKey.getGroup())
                            .scheduleTime(trigger.get(0).getStartTime().toString()).build());

                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();

        }

        return result;
    }

    private void registerJobInScheduler(final JobDetail jobDetail, final Trigger trigger) throws SchedulerException {
        if (scheduler.checkExists(jobDetail.getKey())) {
            scheduler.deleteJob(jobDetail.getKey());
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            scheduler.scheduleJob(jobDetail, trigger);
        }

    }


    public <T extends Job> JobDetail buildJobDetail(
            Class<? extends Job> job, final String jobName, final String group,
            String jobDescription, Map<String, Object> params) {

        JobDataMap jobDataMap = new JobDataMap();

        if (params != null) {
            jobDataMap.putAll(params);
        }

        return JobBuilder.newJob(job)
                .withIdentity(jobName, group)
                .withDescription(jobDescription)
                .usingJobData(jobDataMap)
                .build();
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

    private Trigger buildTrigger(final String name, final String group, final LocalTime startTime) {
        SimpleTriggerFactoryBean triggerFactory = new SimpleTriggerFactoryBean();

        triggerFactory.setName(name);
        triggerFactory.setGroup(group);
        triggerFactory.setStartTime(localTimeToDate(startTime));
        triggerFactory.setRepeatCount(0);
        triggerFactory.setRepeatInterval(0);

        triggerFactory.afterPropertiesSet();
        return triggerFactory.getObject();

    }

    private Date localTimeToDate(final LocalTime startTime) {
        Instant instant = startTime.atDate(LocalDate
                        .of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()))
                .atZone(ZoneId.systemDefault()).toInstant();

        return Date.from(instant);
    }
}
