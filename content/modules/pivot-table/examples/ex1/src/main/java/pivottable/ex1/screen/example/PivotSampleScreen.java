package pivottable.ex1.screen.example;

import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import pivottable.ex1.entity.Day;
import pivottable.ex1.entity.Sex;
import pivottable.ex1.entity.Time;
import pivottable.ex1.entity.TipInfo;

import java.util.ArrayList;
import java.util.List;

// tag::pivot-table[]
@UiController("sample_PivotSampleScreen")
@UiDescriptor("pivot-sample-screen.xml")
public class PivotSampleScreen extends Screen {
    @Autowired
    private CollectionContainer<TipInfo> tipsDc;

    @Subscribe
    protected void onInit(InitEvent event) {
        List<TipInfo> items = new ArrayList<>();
        items.add(tips(16.99, 1.01, Sex.FEMALE, false, Day.FRI, Time.DINNER, 2));
        items.add(tips(10.34, 1.66, Sex.FEMALE, true, Day.THU, Time.LUNCH, 3));
        items.add(tips(21.01, 3.5, Sex.MALE, true, Day.FRI, Time.LUNCH, 3));
        items.add(tips(23.68, 3.31, Sex.FEMALE, false, Day.MON, Time.DINNER, 2));
        items.add(tips(24.59, 3.61, Sex.MALE, false, Day.TUE, Time.LUNCH, 4));
        tipsDc.setItems(items);
    }

    private TipInfo tips(double totalBill, double tip, Sex sex, Boolean smoker, Day day, Time time, int size) {
        TipInfo tips = new TipInfo();
        tips.setTotalBill(totalBill);
        tips.setTip(tip);
        tips.setSex(sex);
        tips.setSmoker(smoker);
        tips.setDay(day);
        tips.setTime(time);
        tips.setSize(size);
        return tips;
    }

}
// end::pivot-table[]