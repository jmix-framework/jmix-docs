package maps.ex1.screen.store;

import io.jmix.ui.screen.*;
import maps.ex1.entity.Store;

@UiController("mapst_Store.browse")
@UiDescriptor("store-browse.xml")
@LookupComponent("storesTable")
public class StoreBrowse extends StandardLookup<Store> {
}