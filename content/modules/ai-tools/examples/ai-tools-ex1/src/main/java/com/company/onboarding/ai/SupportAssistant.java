package com.company.onboarding.ai;

import io.jmix.aitools.service.AiAssistantService;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// tag::assistant-service[]
@Component
public class SupportAssistant {

    @Autowired
    private AiAssistantService aiAssistantService;

    @Nullable
    public String ask(String question) {
        return aiAssistantService.send(question); // <1>
    }
}
// end::assistant-service[]
