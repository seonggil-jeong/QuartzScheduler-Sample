package com.sample.quartz.app.job.vo;

import lombok.Getter;

@Getter
public class CronJobRequest extends JobRequest {
    private final String cronExpression; // cron

    private CronJobRequest(Builder builder) {
        cronExpression = builder.cronExpression;

        super.setJobName(builder.jobName);
        super.setJobGroup(builder.jobGroup);
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private String cronExpression;
        private String jobName;
        private String jobGroup;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder cronExpression(String val) {
            cronExpression = val;
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

        public CronJobRequest build() {
            return new CronJobRequest(this);
        }
    }
}
