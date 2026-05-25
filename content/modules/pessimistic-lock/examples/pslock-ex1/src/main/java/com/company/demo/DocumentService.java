package com.company.demo;

import com.company.demo.entity.Document;
import io.jmix.pessimisticlock.LockManager;
import io.jmix.pessimisticlock.entity.LockInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentService {

    // tag::lock[]
    @Autowired
    private LockManager lockManager;

    public void lockAndProcessDocument(Document document) {
        LockInfo lockInfo = lockManager.lock(document);
        if (lockInfo != null) {
            throw new IllegalStateException("Document is already locked by " +
                    lockInfo.getUsername());
        }
        try {
            processDocument(document);
        } finally {
            lockManager.unlock(document);
        }
    }
    // end::lock[]

    private void processDocument(Document document) {

    }
}
