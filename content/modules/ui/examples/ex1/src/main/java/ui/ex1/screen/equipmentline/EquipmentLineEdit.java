package ui.ex1.screen.equipmentline;

import io.jmix.ui.screen.*;
import ui.ex1.entity.EquipmentLine;

@UiController("uiex1_EquipmentLine.edit")
@UiDescriptor("equipment-line-edit.xml")
@EditedEntityContainer("equipmentLineDc")
public class EquipmentLineEdit extends StandardEditor<EquipmentLine> {
}