package ui.ex1.screen.budgetitem;

import io.jmix.ui.screen.*;
import ui.ex1.entity.BudgetItem;

@UiController("uiex1_BudgetItem.edit")
@UiDescriptor("budget-item-edit.xml")
@EditedEntityContainer("budgetItemDc")
public class BudgetItemEdit extends StandardEditor<BudgetItem> {
}