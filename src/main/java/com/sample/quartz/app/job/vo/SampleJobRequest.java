package com.sample.quartz.app.job.vo;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class SampleJobRequest extends JobRequest {
    private final LocalTime startTime; // 지연 시간
    private final Integer repeatCount;

    private SampleJobRequest(Builder builder) {
        startTime = builder.startTime;
        repeatCount = builder.repeatCount;
        super.setJobName(builder.jobName);
        super.setJobGroup(builder.jobGroup);
        super.setJobDescription(builder.jobDescription);
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private LocalTime startTime;
        private String jobName;
        private String jobGroup;
        private String jobDescription;
        private Integer repeatCount;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder delaySeconds(LocalTime val) {
            startTime = val;
            return this;
        }

        public Builder jobName(String val) {
            jobName = val;
            return this;
        }

        public Builder jobGroup(String val) {
            jobGroup = val;
            return this;
        }

        public Builder jobDescription(String val) {
            jobDescription = val;
            return this;
        }

        public Builder repeatCount(Integer val) {
            repeatCount = val;
            return this;
        }

        public SampleJobRequest build() {
            return new SampleJobRequest(this);
        }
    }
}
