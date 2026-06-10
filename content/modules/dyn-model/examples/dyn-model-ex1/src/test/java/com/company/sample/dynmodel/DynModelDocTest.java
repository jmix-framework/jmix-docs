package com.company.sample.dynmodel;

import com.company.sample.SampleApplication;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.dynmodel.DynamicModelSettingsService;
import io.jmix.dynmodel.entity.DynamicModelSettings;
import io.jmix.flowui.testassist.FlowuiTestAssistConfiguration;
import io.jmix.flowui.testassist.UiTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Validates that the documented dynamic model YAML
 * ({@code com/company/sample/dynmodel/customer-model.yaml}) applies successfully
 * and produces the expected metadata. This keeps the YAML examples included in
 * the documentation valid as the Dynamic Model format evolves.
 * <p>
 * The full model, including the {@code views:} sections (dynamic entity views and
 * the static {@code Customer.list} descriptor override), is applied via
 * {@link DynamicModelSettingsService#applyActive()}.
 * <p>
 * Note: {@code applyActive()} also deploys the dynamic/overridden Flow UI views as
 * a post-publication step. View deployment calls {@code RouteConfiguration}, which
 * requires an initialized Vaadin routing environment. This test therefore runs with
 * {@link UiTest} and {@link FlowuiTestAssistConfiguration} (the same harness used by
 * {@code UserUiTest}), which initializes that environment so the full model — views
 * included — applies in this headless test.
 */
@UiTest
@SpringBootTest(classes = {SampleApplication.class, FlowuiTestAssistConfiguration.class})
@ActiveProfiles("test")
public class DynModelDocTest {

    @Autowired
    DynamicModelSettingsService settingsService;

    @Autowired
    DataManager dataManager;

    @Autowired
    Metadata metadata;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        cleanSettings();
    }

    @AfterEach
    void tearDown() {
        cleanSettings();
    }

    private void cleanSettings() {
        try {
            jdbcTemplate.update("delete from DYNMOD_SETTINGS");
        } catch (Exception e) {
            // ignore: table may not exist yet
        }
    }

    private String readModelYaml() throws Exception {
        ClassPathResource resource = new ClassPathResource("com/company/sample/dynmodel/customer-model.yaml");
        try (InputStream is = resource.getInputStream()) {
            return StreamUtils.copyToString(is, StandardCharsets.UTF_8);
        }
    }

    @Test
    void test_applyDocumentedModel() throws Exception {
        String yaml = readModelYaml();

        DynamicModelSettings settings = dataManager.create(DynamicModelSettings.class);
        settings.setContent(yaml);
        settings.setActive(true);
        dataManager.save(settings);

        settingsService.applyActive();

        // Static Customer entity is extended with dynamic attributes
        MetaClass customer = metadata.getClass("Customer");
        assertThat(customer.findProperty("taxId")).isNotNull();
        assertThat(customer.findProperty("countryCode")).isNotNull();
        assertThat(customer.findProperty("loyaltyLevel")).isNotNull();
        assertThat(customer.findProperty("benefits")).isNotNull();
        assertThat(customer.findProperty("grade")).isNotNull();

        // Dynamic entities are created
        MetaClass loyaltyLevel = metadata.findClass("LoyaltyLevel");
        assertThat(loyaltyLevel).isNotNull();
        assertThat(loyaltyLevel.findProperty("publicSummary")).isNotNull();
        assertThat(loyaltyLevel.findProperty("discount")).isNotNull();

        MetaClass benefit = metadata.findClass("Benefit");
        assertThat(benefit).isNotNull();
    }
}
