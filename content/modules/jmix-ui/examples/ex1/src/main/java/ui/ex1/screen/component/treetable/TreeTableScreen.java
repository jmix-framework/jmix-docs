package ui.ex1.screen.component.treetable;

import io.jmix.ui.action.Action;
import io.jmix.ui.component.TreeTable;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Department;

@UiController("treeTable-screen")
@UiDescriptor("treeTable-screen.xml")
public class TreeTableScreen extends Screen {
    @Autowired
    private TreeTable<Department> treeTable;

    @Subscribe("treeTable.expandTo")
    public void onTreeTableExpandTo(Action.ActionPerformedEvent event) {
        // tag::expand-up-to[]
        treeTable.expandUpTo(1);
        // end::expand-up-to[]

    }
}