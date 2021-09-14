package pivottable.ex1.screen.tipinfo;

import io.jmix.ui.screen.*;
import pivottable.ex1.entity.TipInfo;

@UiController("sample_TipInfo.edit")
@UiDescriptor("tip-info-edit.xml")
@EditedEntityContainer("tipInfoDc")
public class TipInfoEdit extends StandardEditor<TipInfo> {
}