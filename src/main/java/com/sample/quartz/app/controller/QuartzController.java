package com.sample.quartz.app.controller;

import com.sample.quartz.QuartzHandler;
import com.sample.quartz.app.job.SampleJob;
import com.sample.quartz.app.job.vo.JobResponse;
import com.sample.quartz.app.job.vo.SampleJobRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
public class QuartzController {
    private final QuartzHandler quartzHandler;


    @PostMapping("/jobs/{group}/{name}/{minutes}")
    public ResponseEntity<Void> createJob(@PathVariable final String group, @PathVariable final String name,
                                          @PathVariable final Integer minutes) throws Exception {

        quartzHandler.addJob(SampleJob.class, SampleJobRequest.builder()
                .group(group)
                .name(name)
                .userId(UUID.randomUUID())
                .content("[Send Message] at " + LocalDateTime.now())
                .startTime(LocalTime.now().plusMinutes(minutes)).build());

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponse>>findAllJob() throws Exception {
        return ResponseEntity.ok().body(quartzHandler.findAllActivatedJob());
    }
}
