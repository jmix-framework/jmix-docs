package com.company.sample.job;

import io.jmix.uirecovery.ViewDraftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// tag::cleanup-job[]
@Component
public class DraftsCleanupJob {

    private static final Logger log = LoggerFactory.getLogger(DraftsCleanupJob.class);

    private final ViewDraftService viewDraftService;

    public DraftsCleanupJob(ViewDraftService viewDraftService) {
        this.viewDraftService = viewDraftService;
    }

    @Scheduled(cron = "0 0 3 * * *") // <1>
    public void deleteExpiredDrafts() {
        int count = viewDraftService.deleteExpired(); // <2>
        log.info("Deleted {} expired drafts", count);
    }
}
// end::cleanup-job[]
