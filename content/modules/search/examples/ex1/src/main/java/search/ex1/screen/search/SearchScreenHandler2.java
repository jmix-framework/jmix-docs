package search.ex1.screen.search;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.DialogWindows;
import io.jmix.flowui.component.UiComponentUtils;
import io.jmix.flowui.view.*;
import io.jmix.search.searching.SearchResult;
import io.jmix.searchflowui.component.SearchField;
import io.jmix.searchflowui.component.SearchFieldContext;
import io.jmix.searchflowui.view.result.SearchResultsView;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "search-view", layout = MainView.class)
@ViewController("SearchView")
@ViewDescriptor("search-view.xml")
public class SearchView extends StandardView {

    // tag::my-search-field[]
    @ViewComponent
    SearchField mySearchField;

    // end::my-search-field[]

    // tag::screen-builders[]
    @Autowired
    DialogWindows dialogWindows;

    // end::screen-builders[]

    // tag::handler[]
    @Install(to = "mySearchField", subject = "searchCompletedHandler")
    public void mySearchFieldSearchCompletedHandler(
            SearchField.SearchCompletedEvent event) {
        SearchResult searchResult = event.getSearchResult();
        DialogWindow<SearchResultsView> searchResultsDialog =
                dialogWindows.view(UiComponentUtils.getView(this),
                                SearchResultsView.class)
                        .build();
        SearchResultsView view = searchResultsDialog.getView();
        view.initView(new SearchFieldContext(mySearchField));
        searchResultsDialog.open();
    }
    // end::handler[]
}
