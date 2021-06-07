package ui.ex1.screen.data;

import io.jmix.ui.model.DataComponents;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("sample_SmplScreen")
@UiDescriptor("smpl-screen.xml")
// tag::sample-screen[]
public class SmplScreen extends Screen {

    @Autowired
    private DataContext dataContext;

    public void setParentDataContext(DataContext parentDataContext) {
        dataContext.setParent(parentDataContext);
    }

}
// end::sample-screen[]