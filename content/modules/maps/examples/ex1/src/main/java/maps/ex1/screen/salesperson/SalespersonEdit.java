package maps.ex1.screen.salesperson;

import io.jmix.ui.screen.*;
import maps.ex1.entity.Salesperson;

@UiController("mapst_Salesperson.edit")
@UiDescriptor("salesperson-edit.xml")
@EditedEntityContainer("salespersonDc")
public class SalespersonEdit extends StandardEditor<Salesperson> {
}