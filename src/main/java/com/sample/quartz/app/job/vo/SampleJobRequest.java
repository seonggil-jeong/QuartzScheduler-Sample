package com.sample.quartz.app.job.vo;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

@Getter
public class SampleJobRequest extends BaseJobRequest {
    private final UUID userId; // userId
    private final LocalTime startTime; // Desired start time
    private final String content;

    private SampleJobRequest(Builder builder) {
        super.setName(builder.name);
        super.setGroup(builder.group);
        super.setDescription(builder.description);
        userId = builder.userId;
        startTime = builder.startTime;
        content = builder.content;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private @NotNull(message = "name cannot be Null") String name;
        private @NotNull(message = "group cannot be Null") String group;
        private String description;
        private UUID userId;
        private LocalTime startTime;
        private String content;

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

        public Builder userId(UUID val) {
            userId = val;
            return this;
        }

        public Builder startTime(LocalTime val) {
            startTime = val;
            return this;
        }

        public Builder content(String val) {
            content = val;
            return this;
        }

        public SampleJobRequest build() {
            return new SampleJobRequest(this);
        }
    }
}
