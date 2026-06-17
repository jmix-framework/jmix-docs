package com.company.demo.service;

import io.jmix.search.searching.SearchRequestContext;
import io.jmix.searchopensearch.searching.strategy.OpenSearchQueryConfigurer;
import io.jmix.searchopensearch.searching.strategy.impl.AbstractOpenSearchStrategy;
import org.opensearch.client.opensearch._types.query_dsl.Operator;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.springframework.stereotype.Component;

// tag::strategy[]
@Component
public class CustomOpenSearchSearchStrategy extends AbstractOpenSearchStrategy {

    public CustomOpenSearchSearchStrategy(OpenSearchQueryConfigurer queryConfigurer) {
        super(queryConfigurer); // <1>
    }

    @Override
    public String getName() {
        return "CustomStrategy"; // <2>
    }

    @Override
    public void configureRequest(SearchRequestContext<SearchRequest.Builder> requestContext) {
        queryConfigurer.configureRequest( // <3>
                requestContext,
                (queryBuilder, scope) -> // <4>
                        queryBuilder.multiMatch(multiMatchQueryBuilder ->
                                multiMatchQueryBuilder
                                        .fields(scope.getFieldList()) // <5>
                                        .query(requestContext.getSearchContext().getEscapedSearchText())
                                        .operator(Operator.Or)
                        )
        );
    }
}
// end::strategy[]
