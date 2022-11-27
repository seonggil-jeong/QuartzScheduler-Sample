package com.sample.quartz.app.job.vo;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CronJobRequest extends BaseJobRequest {
    private final String cronExpression; // cron

    private CronJobRequest(Builder builder) {
        super.setName(builder.name);
        super.setGroup(builder.group);
        super.setDescription(builder.description);
        cronExpression = builder.cronExpression;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private @NotNull(message = "name cannot be Null") String name;
        private @NotNull(message = "group cannot be Null") String group;
        private String description;
        private String cronExpression;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder name(@NotNull(message = "name cannot be Null") String val) {
            name = val;
            return this;
        }

        public Builder group(@NotNull(message = "group cannot be Null") String val) {
            group = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder cronExpression(String val) {
            cronExpression = val;
            return this;
        }

        public CronJobRequest build() {
            return new CronJobRequest(this);
        }
    }
}
