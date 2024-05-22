package com.company.onboarding.view.webdavdocumentwrapper;

import com.company.onboarding.entity.WebdavDocumentWrapper;

import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.core.AccessManager;
import io.jmix.core.FileRef;
import io.jmix.core.Metadata;
import io.jmix.core.accesscontext.CrudEntityContext;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.action.list.CreateAction;
import io.jmix.flowui.action.list.EditAction;
import io.jmix.flowui.action.list.RemoveAction;
import io.jmix.flowui.component.formatter.DateFormatter;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.action.BaseAction;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.webdav.entity.WebdavDocument;
import io.jmix.webdav.entity.WebdavDocumentVersion;
import io.jmix.webdavflowui.component.WebdavDocumentLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Date;

@Route(value = "webdavDocumentWrappers", layout = MainView.class)
@ViewController("WebdavDocumentWrapper.list")
@ViewDescriptor("webdav-document-wrapper-list-view.xml")
@LookupComponent("webdavDocumentWrappersDataGrid")
@DialogMode(width = "64em")
public class WebdavDocumentWrapperListView extends StandardListView<WebdavDocumentWrapper> {
    @Autowired
    private UiComponents uiComponents;
    // tag::webdavDocumentWrappersDataGrid[]
    @ViewComponent
    private DataGrid<WebdavDocumentWrapper> webdavDocumentWrappersDataGrid;

    // end::webdavDocumentWrappersDataGrid[]

    // tag::downloader[]
    @Autowired
    private Downloader downloader; // <1>

    // end::downloader[]
    // tag::buttons-actions[]
    @ViewComponent
    private JmixButton downloadBtn;
    @ViewComponent
    private JmixButton createBtn;
    @ViewComponent
    private JmixButton editBtn;
    @ViewComponent
    private JmixButton removeBtn;
    @ViewComponent("webdavDocumentWrappersDataGrid.download")
    private BaseAction webdavDocumentWrappersDataGridDownload;
    @ViewComponent("webdavDocumentWrappersDataGrid.create")
    private CreateAction<WebdavDocumentWrapper> webdavDocumentWrappersDataGridCreate;
    @ViewComponent("webdavDocumentWrappersDataGrid.edit")
    private EditAction<WebdavDocumentWrapper> webdavDocumentWrappersDataGridEdit;
    @ViewComponent("webdavDocumentWrappersDataGrid.remove")
    private RemoveAction<WebdavDocumentWrapper> webdavDocumentWrappersDataGridRemove;
    @Autowired
    private Metadata metadata;
    @Autowired
    private AccessManager accessManager;

    // end::buttons-actions[]
    // tag::lastModifiedDate-renderer[]
    @Autowired
    private ApplicationContext applicationContext;

    @Supply(to = "webdavDocumentWrappersDataGrid.lastModifiedDate", subject = "renderer")
    private Renderer<WebdavDocumentWrapper> webdavDocumentWrappersDataGridLastModifiedDateRenderer() {
        DateFormatter dateFormatter = applicationContext.getBean(DateFormatter.class);
        return new TextRenderer<>(documentWrapper -> {
            WebdavDocument webdavDocument = documentWrapper.getWebdavDocument();
            if (webdavDocument == null) {
                return null;
            }

            WebdavDocumentVersion lastVersion = webdavDocument.getLastVersion();
            Date lastModifiedDate = lastVersion.getCreatedDate();
            dateFormatter.setFormat("MMM dd, yyyy");
            return dateFormatter.apply(lastModifiedDate);
        });
    }

    // end::lastModifiedDate-renderer[]

    // tag::webdavDocument-renderer[]
    @Supply(to = "webdavDocumentWrappersDataGrid.webdavDocument", subject = "renderer")
    private Renderer<WebdavDocumentWrapper> webdavDocumentWrappersDataGridWebdavDocumentRenderer() {
        return new ComponentRenderer<>( // <1>
                () -> uiComponents.create(WebdavDocumentLink.class), // <2>
                (link, wrapper) -> {
                    WebdavDocument webdavDocument = wrapper.getWebdavDocument();
                    if (webdavDocument != null) {
                        link.setWebdavDocument(webdavDocument);
                    }
                });
    }

    // end::webdavDocument-renderer[]
    // tag::lastModifiedBy-renderer[]
    @Supply(to = "webdavDocumentWrappersDataGrid.lastModifiedBy", subject = "renderer")
    private Renderer<WebdavDocumentWrapper> webdavDocumentWrappersDataGridLastModifiedByRenderer() {
        return new TextRenderer<>(documentWrapper -> { // <1>
            WebdavDocument webdavDocument = documentWrapper.getWebdavDocument();
            if (webdavDocument == null) {
                return null;
            }

            WebdavDocumentVersion lastVersion = webdavDocument.getLastVersion();
            return lastVersion.getCreatedBy(); // <2>
        });
    }

    // end::lastModifiedBy-renderer[]
    // tag::download[]
    @Subscribe("webdavDocumentWrappersDataGrid.download")
    public void onWebdavDocumentWrappersDataGridDownload(final ActionPerformedEvent event) {
        WebdavDocumentWrapper webdavDocumentWrapper = webdavDocumentWrappersDataGrid.getSingleSelectedItem();
        if (webdavDocumentWrapper == null) {
            return;
        }

        WebdavDocument webdavDocument = webdavDocumentWrapper.getWebdavDocument();
        if (webdavDocument == null) {
            return;
        }
        WebdavDocumentVersion lastVersion = webdavDocument.getLastVersion(); // <2>
        FileRef fileReference = lastVersion.getFileReference(); // <3>
        downloader.download(fileReference); // <4>
    }
    // end::download[]

    // tag::customizing-ui[]
    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        setupDocumentsButtons();
        onDocumentSelect(null);
    }

    protected void setupDocumentsButtons() {
        CrudEntityContext accessContext = getDocumentsAccessContext();
        downloadBtn.setVisible(accessContext.isReadPermitted());
        createBtn.setVisible(accessContext.isCreatePermitted());
        editBtn.setVisible(accessContext.isUpdatePermitted());
        removeBtn.setVisible(accessContext.isDeletePermitted());

        webdavDocumentWrappersDataGridDownload.setVisible(accessContext.isReadPermitted());
        webdavDocumentWrappersDataGridCreate.setVisible(accessContext.isCreatePermitted());
        webdavDocumentWrappersDataGridEdit.setVisible(accessContext.isUpdatePermitted());
        webdavDocumentWrappersDataGridRemove.setVisible(accessContext.isDeletePermitted());
    }
    protected CrudEntityContext getDocumentsAccessContext() {
        MetaClass metaClass = metadata.getClass(WebdavDocumentWrapper.class);
        CrudEntityContext accessContext = new CrudEntityContext(metaClass);
        accessManager.applyRegisteredConstraints(accessContext);
        return accessContext;
    }

    protected void onDocumentSelect(WebdavDocumentWrapper selectedDocument) {
        boolean hasWebdavDocument = selectedDocument != null && selectedDocument.getWebdavDocument() != null;
        webdavDocumentWrappersDataGridDownload.setEnabled(hasWebdavDocument);
        downloadBtn.setEnabled(hasWebdavDocument);
    }

    @Subscribe("webdavDocumentWrappersDataGrid")
    public void onWebdavDocumentWrappersDataGridItemClick(final ItemClickEvent<WebdavDocumentWrapper> event) {
        onDocumentSelect(webdavDocumentWrappersDataGrid.getSingleSelectedItem());
    }
// end::customizing-ui[]
}