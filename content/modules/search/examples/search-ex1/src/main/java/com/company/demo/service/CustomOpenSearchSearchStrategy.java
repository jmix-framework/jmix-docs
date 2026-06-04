package com.company.demo.service;

import io.jmix.search.searching.SearchRequestContext;
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
    public void configureRequest(SearchRequestContext<SearchRequest.Builder> searchRequestContext) {
        //configure your request
        searchRequestContext.getRequestBuilder().query(queryBuilder ->
                queryBuilder.multiMatch(multiMatchQueryBuilder ->
                        multiMatchQueryBuilder.fields("*")
                                .query(searchRequestContext.getSearchContext().getSearchText())
                )
        );
    }
}
// end::strategy[]
