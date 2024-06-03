package com.company.supersetsample.app;

import io.jmix.core.security.CurrentAuthentication;
import io.jmix.superset.SupersetTokenManager;
import io.jmix.superset.client.SupersetClient;
import io.jmix.superset.client.model.GuestTokenBody;
import io.jmix.supersetflowui.SupersetGuestTokenProvider;
import io.jmix.supersetflowui.component.dataconstraint.DatasetConstraint;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static io.jmix.superset.client.model.GuestTokenBody.Resource.DASHBOARD_TYPE;

// tag::custom-guest-token-provider[]
@Component
public class CustomGuestTokenProvider implements SupersetGuestTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(CustomGuestTokenProvider.class);

    private CurrentAuthentication currentAuthentication;
    private TaskExecutor taskExecutor;
    private SupersetClient supersetClient;
    private SupersetTokenManager tokenManager;

    public CustomGuestTokenProvider(CurrentAuthentication currentAuthentication,
                                    SupersetClient supersetClient,
                                    SupersetTokenManager tokenManager,
                                    TaskExecutor taskExecutor) {
        this.currentAuthentication = currentAuthentication;
        this.supersetClient = supersetClient;
        this.tokenManager = tokenManager;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void fetchGuestToken(FetchGuestTokenContext context, Consumer<String> callback) {
        String username = currentAuthentication.getUser().getUsername();
        taskExecutor.execute(() -> {
            try {
                String guestToken = supersetClient
                        .fetchGuestToken(
                                buildGuestTokenBody(context, username),
                                tokenManager.getAccessToken(),
                                tokenManager.getCsrfToken())
                        .getToken();

                context.getSource().getUI()
                        .ifPresent(ui -> ui.access(() -> callback.accept(guestToken)));
            } catch (IOException | InterruptedException e) {
                log.error("Could not fetch guest token", e);
            }
        });
    }

    protected GuestTokenBody buildGuestTokenBody(FetchGuestTokenContext context, String username) {
        List<GuestTokenBody.RowLevelRole> rls = Collections.emptyList();
        if (context.getDatasetConstraintsProvider() != null) {
            rls = convertToSupersetRls(context.getDatasetConstraintsProvider().getConstraints());
        }

        return GuestTokenBody.builder()
                .withResource(new GuestTokenBody.Resource()
                        .withId(context.getEmbeddedId())
                        .withType(DASHBOARD_TYPE))
                .withRowLevelRoles(rls)
                .withUser(new GuestTokenBody.User()
                        .withUsername(username))
                .build();
    }

    protected List<GuestTokenBody.RowLevelRole> convertToSupersetRls(List<DatasetConstraint> datasetConstraints) {
        return CollectionUtils.isNotEmpty(datasetConstraints)
                ? datasetConstraints.stream()
                .map(dc -> new GuestTokenBody.RowLevelRole()
                        .withClause(dc.clause())
                        .withDataset(dc.dataset()))
                .toList()
                : Collections.emptyList();
    }
}
// end::custom-guest-token-provider[]