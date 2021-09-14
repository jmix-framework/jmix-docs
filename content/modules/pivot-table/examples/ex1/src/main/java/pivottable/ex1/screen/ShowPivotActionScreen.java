package pivottable.ex1.screen;

import io.jmix.pivottable.action.list.PivotScreenBuilder;
import io.jmix.pivottable.action.list.ShowPivotAction;
import pivottable.ex1.entity.TipInfo;
import io.jmix.ui.Actions;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collection;

@UiController("sample_ShowPivotActionScreen")
@UiDescriptor("show-pivot-action-screen.xml")
public class ShowPivotActionScreen extends Screen {

    // tag::actions[]
    @Autowired
    private Actions actions;

    // end::actions[]

    // tag::button[]
    @Autowired
    private Button defaultShowPivotBtn;

    // end::button[]

    // tag::tips-table[]
    @Autowired
    private GroupTable<TipInfo> tipsTable;

    // end::tips-table[]

    // tag::init[]
    @Subscribe
    public void onInit(InitEvent event) {
        ShowPivotAction showPivotAction = actions.create(ShowPivotAction.class);
        showPivotAction.setTarget(tipsTable);
        defaultShowPivotBtn.setAction(showPivotAction);
    }
    // end::init[]

    // tag::custom[]
    @Subscribe("customShowPivotBtn")
    public void onCustomShowPivotBtnClick(Button.ClickEvent event) {
        PivotScreenBuilder builder = getApplicationContext()
                .getBean(PivotScreenBuilder.class, tipsTable);
        builder.withIncludedProperties(Arrays.asList("sex", "smoker", "time", "day")) // <1>
                .withNativeJson( // <2>
                        "{"
                                + " \"cols\": [\"Time\", \"Day\"],"
                                + " \"rows\": [\"Sex\", \"Smoker\"],"
                                + "\"renderers\": {"
                                + "  \"selectedRenderer\": \"table\","
                                + "  \"renderers\": [\"table\", \"tableBarchart\", \"heatmap\","
                                + "                  \"rowHeatmap\", \"colHeatmap\"]"
                                + "  }"
                                + "}")
                .withItems((Collection) tipsTable.getItems().getItems())
                .build()
                .show();
    }
    // end::custom[]
}