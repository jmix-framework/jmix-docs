package ui.ex1.screen.component.tree;

import io.jmix.ui.Notifications;
import io.jmix.ui.component.Tree;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ui.ex1.entity.Department;

@UiController("tree-screen")
@UiDescriptor("tree-screen.xml")
public class TreeScreen extends Screen {
    @Autowired
    private Notifications notifications;
    @Autowired
    private Tree<Department> simpleTree;
    @Autowired
    private Tree<Department> treeWithSelectionEvent;

    // tag::description-provider[]
    @Install(to = "simpleTree", subject = "descriptionProvider")
    private String simpleTreeDescriptionProvider(Department department) {
        return "The manager of the " + department.getName() +
                " department is " + department.getManager().getName();
    }
    // end::description-provider[]
    // tag::collapse-event[]
    @Subscribe("deptTree")
    public void onDeptTreeCollapse(Tree.CollapseEvent<Department> event) {
        notifications.create()
                .withCaption("Collapsed: " + event.getCollapsedItem().getName())
                .show();
    }
    // end::collapse-event[]
    // tag::expand-event[]
    @Subscribe("deptTree")
    public void onDeptTreeExpand(Tree.ExpandEvent<Department> event) {
        notifications.create()
                .withCaption("Expanded: " + event.getExpandedItem().getName())
                .show();
    }
    // end::expand-event[]
    // tag::selection-event[]
    @Subscribe("treeWithSelectionEvent")
    public void onTreeWithSelectionEventSelection(Tree.SelectionEvent<Department> event) {
        Department department = treeWithSelectionEvent.getSingleSelected();
        notifications.create()
                .withCaption("You selected " + department.getName() + " department")
                .show();
    }
    // end::selection-event[]
    // tag::item-caption-provider[]
    @Install(to = "departmentTree", subject = "itemCaptionProvider")
    private String departmentTreeItemCaptionProvider(Department department) {
        return "Department: " + department.getName() +
                ", manager: " + department.getManager().getName();
    }
    // end::item-caption-provider[]
}