package com.company.demo.view.search;


import com.company.demo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewComponent;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.searchflowui.component.SearchField;

@Route(value = "my-search-view", layout = MainView.class)
@ViewController("MySearchView")
@ViewDescriptor("my-search-view.xml")
public class MySearchView extends StandardView {
    @ViewComponent
    private SearchField searchField;
}