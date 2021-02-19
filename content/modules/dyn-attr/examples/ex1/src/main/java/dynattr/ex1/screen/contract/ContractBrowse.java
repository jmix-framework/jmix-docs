package dynattr.ex1.screen.contract;

import io.jmix.ui.screen.*;
import dynattr.ex1.entity.Contract;

@UiController("sample_Contract.browse")
@UiDescriptor("contract-browse.xml")
@LookupComponent("contractsTable")
public class ContractBrowse extends StandardLookup<Contract> {

}