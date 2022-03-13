package ui.ex1.screen.entity.budgetitem;

import io.jmix.ui.screen.*;
import ui.ex1.entity.BudgetItem;

@UiController("uiex1_BudgetItem.browse")
@UiDescriptor("budget-item-browse.xml")
@LookupComponent("budgetItemsTable")
public class BudgetItemBrowse extends StandardLookup<BudgetItem> {
}