package com.company.demo.service;

import io.jmix.search.index.queue.IndexingQueueManager;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

// tag::custom-job[]
public class MyCustomQueueProcessingJob implements Job {

    @Autowired
    private IndexingQueueManager indexingQueueManager;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        indexingQueueManager.processNextBatch();
    }
}
// end::custom-job[]
