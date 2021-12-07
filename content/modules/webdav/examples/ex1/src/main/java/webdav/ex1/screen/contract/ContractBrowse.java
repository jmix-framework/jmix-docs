package webdav.ex1.screen.contract;

import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.screen.*;
import io.jmix.webdavui.component.WebdavDocumentLink;
import org.springframework.beans.factory.annotation.Autowired;
import webdav.ex1.entity.Contract;

@UiController("sample_Contract.browse")
@UiDescriptor("contract-browse.xml")
@LookupComponent("contractsTable")
public class ContractBrowse extends StandardLookup<Contract> {

    // tag::ui-components[]
    @Autowired
    protected UiComponents uiComponents;

    // end::ui-components[]

    // tag::column-generator[]
    @Install(to = "contractsTable.docLink", subject = "columnGenerator")
    protected Component contractsTableDocLinkColumnGenerator(Contract contract) {
        WebdavDocumentLink link = uiComponents.create(WebdavDocumentLink.class)
                .withWebdavDocument(contract.getDocument()); // <1>
        link.setIsShowVersion(false); // <2>
        return link;
    }
    // end::column-generator[]
}