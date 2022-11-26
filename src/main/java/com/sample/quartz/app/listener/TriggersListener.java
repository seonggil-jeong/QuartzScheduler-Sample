package com.sample.quartz.app.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * Trigger 실행 전후에 Event 실행
 */
@Slf4j
public class TriggersListener implements TriggerListener {

    @Override
    public String getName() {
        return "TriggersListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.info("Trigger is Starting");
        final JobKey jobKey = trigger.getJobKey();

        log.info("triggerFired at {} :: jobKey : {}", trigger.getStartTime(), jobKey);

    }

    /**
     * @Content : if this return value is true, request to JobListener jobExecutionVetoed()
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        log.info("check Trigger health");

        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        log.info("Trigger Misfired");
        final JobKey jobKey = trigger.getJobKey();

        log.info("triggerMisfired at {} :: jobKey : {}", trigger.getStartTime(), jobKey);

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
                                Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        log.info("Trigger Complete");
        final JobKey jobKey = trigger.getJobKey();

        log.info("triggerMisfired at {} :: jobKey : {}", trigger.getStartTime(), jobKey);

    }
}
