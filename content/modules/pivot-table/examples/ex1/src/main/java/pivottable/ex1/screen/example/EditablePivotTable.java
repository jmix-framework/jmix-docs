package pivottable.ex1.screen.example;

import io.jmix.pivottable.component.PivotTable;
import io.jmix.pivottable.model.JsFunction;
import io.jmix.ui.Notifications;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_EditablePivotTable")
@UiDescriptor("editable-pivot-table.xml")
public class EditablePivotTable extends Screen {

    // tag::pivot-table[]
    @Autowired
    private PivotTable pivotTable;

    // end::pivot-table[]

    // tag::notifications[]
    @Autowired
    private Notifications notifications;

    // end::notifications[]

    // tag::init1[]
    @Subscribe
    public void onInit(InitEvent initEvent) {
        // end::init1[]
        // tag::cell-click[]
        pivotTable.addCellClickListener(event -> {
            notifications.create().withCaption("Value: " + event.getValue() + ",\n"
                    + "Filters applied: " + event.getFilters())
                    .show();
        });
        // end::cell-click[]

        // tag::refresh[]
        pivotTable.addRefreshListener(event -> {
            notifications.create().withCaption("Row order :" + event.getRowOrder() + ",\n"
                    + "Inclusions: " + event.getInclusions())
                    .show();
        });
        // end::refresh[]
        // tag::init2[]
    }
    // end::init2[]
}