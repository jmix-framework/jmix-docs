package com.company.demo.service;

import io.jmix.search.searching.SearchContext;
import io.jmix.searchopensearch.searching.strategy.OpenSearchSearchStrategy;
import org.opensearch.client.opensearch.core.SearchRequest;
import org.springframework.stereotype.Component;

// tag::strategy[]
@Component
public class CustomOpenSearchSearchStrategy implements OpenSearchSearchStrategy {

    @Override
    public String getName() {
        return "CustomStrategy";
    }

    @Override
    public void configureRequest(SearchRequest.Builder requestBuilder, SearchContext searchContext) {
        //configure your request
        requestBuilder.query(queryBuilder ->
                queryBuilder.multiMatch(multiMatchQueryBuilder ->
                        multiMatchQueryBuilder.fields("*")
                                .query(searchContext.getSearchText())
                )
        );
    }
}
// end::strategy[]
