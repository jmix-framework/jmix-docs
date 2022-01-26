package reports.ex2.screen.entity.literaturetype;

import io.jmix.ui.screen.*;
import reports.ex2.entity.LiteratureType;

@UiController("jmxrpr_LiteratureType.browse")
@UiDescriptor("literature-type-browse.xml")
@LookupComponent("literatureTypesTable")
public class LiteratureTypeBrowse extends StandardLookup<LiteratureType> {
}