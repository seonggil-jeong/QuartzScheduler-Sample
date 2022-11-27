package com.sample.quartz.app.job.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class JobResponse {
    private final String jobName;
    private final String groupName;
    private final String scheduleTime;
}
