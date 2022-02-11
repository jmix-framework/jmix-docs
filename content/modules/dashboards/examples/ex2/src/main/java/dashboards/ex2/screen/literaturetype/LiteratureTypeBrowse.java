package dashboards.ex2.screen.literaturetype;

import io.jmix.ui.screen.*;
import dashboards.ex2.entity.LiteratureType;

@UiController("sample_LiteratureType.browse")
@UiDescriptor("literature-type-browse.xml")
@LookupComponent("literatureTypesTable")
public class LiteratureTypeBrowse extends StandardLookup<LiteratureType> {
}