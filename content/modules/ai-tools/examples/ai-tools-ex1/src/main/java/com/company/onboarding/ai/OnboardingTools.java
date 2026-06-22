package com.company.onboarding.ai;

import com.company.onboarding.entity.Step;
import io.jmix.aitools.tool.AiToolStatusPublisher;
import io.jmix.aitools.tool.JmixAiTool;
import io.jmix.core.DataManager;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// tag::custom-tool[]
@Component
public class OnboardingTools implements JmixAiTool { // <1>

    @Autowired
    private DataManager dataManager;
    @Autowired
    private AiToolStatusPublisher statusPublisher;

    @Tool(name = "getStepCatalog", // <2>
            description = "Returns the catalog of onboarding steps with their duration in days.")
    public String getStepCatalog(ToolContext toolContext) { // <3>
        String message = "Loading the onboarding step catalog";
        statusPublisher.update(message, toolContext); // <4>

        List<Step> steps = dataManager.load(Step.class).all().list(); // <5>

        statusPublisher.complete(message, steps.size() + " steps", toolContext);

        return steps.stream()
                .sorted(Comparator.comparing(Step::getSortValue))
                .map(step -> step.getName() + " — " + step.getDuration() + " day(s)")
                .collect(Collectors.joining("\n"));
    }
}
// end::custom-tool[]
