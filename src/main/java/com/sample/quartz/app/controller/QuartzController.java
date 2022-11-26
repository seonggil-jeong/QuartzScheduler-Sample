package com.sample.quartz.app.controller;

import com.sample.quartz.QuartzHandler;
import com.sample.quartz.app.job.SampleJob;
import com.sample.quartz.app.job.vo.SampleJobRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
public class QuartzController {
    private final QuartzHandler quartzHandler;


    @RequestMapping("/jobs/{jobName}/{minute}")
    public void createJobSample(@PathVariable final  String jobName, @PathVariable final Integer minute) throws SchedulerException {

        log.info(this.getClass().getName() + ".create Job!");
        log.info("start Time : " + LocalTime.now().plusMinutes(minute));

        quartzHandler.addJob(SampleJob.class, SampleJobRequest.builder()
                .delaySeconds(LocalTime.now().plusMinutes(minute))
                .jobName(jobName) // cannot be duplicated
                .jobDescription("in " + minute + "m Start")
                .repeatCount(0)
                .jobGroup("simpleGroup")
                .build());

    }
}
