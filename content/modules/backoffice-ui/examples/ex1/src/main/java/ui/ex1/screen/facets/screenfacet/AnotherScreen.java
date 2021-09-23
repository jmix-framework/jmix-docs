package ui.ex1.screen.facets.screenfacet;

import io.jmix.ui.component.Label;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

// tag::another-screen[]
@UiController("sample_AnotherScreen")
@UiDescriptor("another-screen.xml")
public class AnotherScreen extends Screen {

    @Autowired
    private Label<Integer> label; //<1>

    private Integer num;

    public void setNum(Integer num) { //<2>
        this.num = num;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) { //<3>
        label.setValue(num);
    }
}
// end::another-screen[]