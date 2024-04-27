package com.company.onboarding.view.webdavdocumentwrapper;

import com.company.onboarding.entity.WebdavDocumentWrapper;

import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.formatter.DateFormatter;
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
            return dateFormatter.apply(lastModifiedDate);
        });
    }

    // end::lastModifiedDate-renderer[]
    @Autowired
    private UiComponents uiComponents;

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
}