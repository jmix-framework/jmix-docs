package search.ex1.screen.search;

import io.jmix.search.searching.EntitySearcher;
import io.jmix.search.searching.SearchContext;
import io.jmix.search.searching.SearchResult;
import io.jmix.search.searching.SearchResultProcessor;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.DataGrid;
import io.jmix.ui.component.TreeDataGrid;
import io.jmix.ui.component.data.DataGridItems;
import io.jmix.ui.component.data.datagrid.ContainerDataGridItems;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import search.ex1.entity.Order;

import java.util.Collection;

@UiController("sample_UsingApiScreen")
@UiDescriptor("using-api-screen.xml")
public class UsingApiScreen extends Screen {

    // tag::entity-searcher[]
    @Autowired
    private EntitySearcher entitySearcher;

    // end::entity-searcher[]

    // tag::search-result-processor[]
    @Autowired
    private SearchResultProcessor searchResultProcessor;

    // end::search-result-processor[]

    // tag::click-event[]
    @Subscribe("searchBtn") // <1>
    public void onSearchBtnClick(Button.ClickEvent event) {
        SearchContext searchContext = new SearchContext("silver") // <2>
                .setSize(20) // <3>
                .setEntities("search_Order"); // <4>
        SearchResult searchResult = entitySearcher.search(searchContext); // <5>
        Collection<Object> instances =
                searchResultProcessor.loadEntityInstances(searchResult); // <6>
        // ...
    }
    // end::click-event[]
}