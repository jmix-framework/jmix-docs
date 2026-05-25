package maps.ex1.screen.salesperson;

import io.jmix.ui.screen.*;
import maps.ex1.entity.Salesperson;

@UiController("mapst_Salesperson.browse")
@UiDescriptor("salesperson-browse.xml")
@LookupComponent("salespersonsTable")
public class SalespersonBrowse extends StandardLookup<Salesperson> {
}