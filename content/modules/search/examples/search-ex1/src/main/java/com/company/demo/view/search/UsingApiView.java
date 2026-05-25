package com.company.demo.view.search;


import com.company.demo.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.search.searching.EntitySearcher;
import io.jmix.search.searching.SearchContext;
import io.jmix.search.searching.SearchResult;
import io.jmix.search.searching.SearchResultProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Route(value = "using-api-view", layout = MainView.class)
@ViewController("UsingApiView")
@ViewDescriptor("using-api-view.xml")
public class UsingApiView extends StandardView {
    // tag::inject[]
    @Autowired
    private EntitySearcher entitySearcher;

    @Autowired
    private SearchResultProcessor searchResultProcessor;

    // end::inject[]

    // tag::click-event[]
    @Subscribe(id = "searchBtn", subject = "clickListener") // <1>
    public void onSearchBtnClick(final ClickEvent<JmixButton> event) {
        SearchContext searchContext = new SearchContext("silver") // <2>
                .setSize(20) // <3>
                .setEntities("Order_"); // <4>
        SearchResult searchResult = entitySearcher.search(searchContext); // <5>
        Collection<Object> instances =
                searchResultProcessor.loadEntityInstances(searchResult); // <6>
        // ...
    }
    // end::click-event[]
}