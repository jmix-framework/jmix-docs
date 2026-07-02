package com.company.onboarding.ai;

import io.jmix.aitools.dataload.introspection.AvailableEntityService;
import io.jmix.aitools.dataload.introspection.model.EntitySummary;
import io.jmix.aitools.dataload.tool.DataLoadAiTool;
import io.jmix.aitools.tool.ToolOverride;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

// tag::tool-override[]
@Component
public class SortedEntitiesTool implements DataLoadAiTool { // <1>

    @Autowired
    private AvailableEntityService availableEntityService;

    @Tool(description = "Returns entities available to the user, ordered by localized name.")
    @ToolOverride("aitls_getAvailableEntities") // <2>
    public List<EntitySummary> getAvailableEntities() {
        return availableEntityService.getEntitySummaries().stream() // <3>
                .sorted(Comparator.comparing(this::firstLocalizedName))
                .toList();
    }

    private String firstLocalizedName(EntitySummary summary) {
        return summary.getLocalizedNames().isEmpty()
                ? summary.getEntityName()
                : summary.getLocalizedNames().get(0);
    }
}
// end::tool-override[]
