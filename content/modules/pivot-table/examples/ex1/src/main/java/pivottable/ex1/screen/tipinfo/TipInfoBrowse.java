package pivottable.ex1.screen.tipinfo;

import io.jmix.ui.screen.*;
import pivottable.ex1.entity.TipInfo;

@UiController("sample_TipInfo.browse")
@UiDescriptor("tip-info-browse.xml")
@LookupComponent("tipInfoesTable")
public class TipInfoBrowse extends StandardLookup<TipInfo> {
}