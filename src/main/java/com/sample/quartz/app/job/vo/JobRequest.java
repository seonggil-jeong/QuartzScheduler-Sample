package com.sample.quartz.app.job.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Objects;


@Getter
@Setter
public abstract class JobRequest {
    @NotBlank
    private String jobName;
    @NotBlank
    private String jobDescription;
    @NotBlank
    private String jobGroup;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobRequest that = (JobRequest) o;
        return jobName.equals(that.jobName) && jobGroup.equals(that.jobGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobName, jobGroup);
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
}
