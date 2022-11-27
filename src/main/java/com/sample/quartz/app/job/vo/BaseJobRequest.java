package com.sample.quartz.app.job.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Getter
@Setter
public abstract class BaseJobRequest {
    @NotNull(message = "name cannot be Null")
    private String name;
    @NotNull(message = "group cannot be Null")
    private String group;
    private String description;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseJobRequest that = (BaseJobRequest) o;
        return name.equals(that.name) && group.equals(that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, group);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
