package com.company.demo.observability;

import io.opentelemetry.sdk.trace.SpanProcessor;
import io.otel.pyroscope.PyroscopeOtelSpanProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Links Pyroscope CPU profiles to root spans. Requires the Pyroscope Java agent,
 * so the configuration is enabled only when {@code demo.pyroscope.span-profiles=true}.
 */
@Configuration
@ConditionalOnProperty(name = "demo.pyroscope.span-profiles", havingValue = "true")
public class SpanProfilesConfiguration {

    // tag::span-processor[]
    @Bean
    public SpanProcessor pyroscopeSpanProcessor() {
        return new PyroscopeOtelSpanProcessor();
    }
    // end::span-processor[]
}
