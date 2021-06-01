package ui.ex1.screen.component.pagination;

import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Pagination;
import io.jmix.ui.component.PaginationComponent;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("paginationScreen")
@UiDescriptor("pagination-screen.xml")
public class PaginationScreen extends Screen {
    @Autowired
    private Notifications notifications;
    @Autowired
    private DataManager dataManager;

    // tag::page-change-event[]
    @Subscribe("pagination")
    public void onPaginationPageChange(Pagination.PageChangeEvent event) {
        notifications.create()
                .withCaption("Selected page: " + event.getPageNumber())
                .show();
    }
    // end::page-change-event[]
    // tag::before-refresh-event[]
    @Subscribe("paginationWithDefault")
    public void onPaginationWithDefaultBeforeRefresh(PaginationComponent.BeforeRefreshEvent event) {
        if (event.getSource().getDataBinder().getCount() > 10) // <1>
            event.preventRefresh(); // <2>
    }
    // end::before-refresh-event[]
    // tag::after-refresh-event[]
    @Subscribe("paginationWithDefault")
    public void onPaginationWithDefaultAfterRefresh(PaginationComponent.AfterRefreshEvent event) {
        notifications.create()
                .withCaption("The data was successfully refreshed!")
                .show();
    }
    // end::after-refresh-event[]
    // tag::total-count-delegate[]
    @Install(to = "pagination", subject = "totalCountDelegate")
    private Integer paginationTotalCountDelegate() {
        return dataManager.loadValue("select count(e) from uiex1_Customer e", Integer.class)
                .one();
    }
    // end::total-count-delegate[]
}