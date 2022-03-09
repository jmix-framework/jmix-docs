package ui.ex1.screen.equipmentline;

import io.jmix.ui.screen.*;
import ui.ex1.entity.EquipmentLine;

@UiController("uiex1_EquipmentLine.browse")
@UiDescriptor("equipment-line-browse.xml")
@LookupComponent("equipmentLinesTable")
public class EquipmentLineBrowse extends StandardLookup<EquipmentLine> {
}