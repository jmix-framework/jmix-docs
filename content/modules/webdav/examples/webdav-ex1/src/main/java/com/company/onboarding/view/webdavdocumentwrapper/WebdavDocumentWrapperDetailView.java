package com.company.onboarding.view.webdavdocumentwrapper;

import com.company.onboarding.entity.WebdavDocumentWrapper;

import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;
import io.jmix.webdavflowui.component.WebdavDocumentUploadField;

@Route(value = "webdavDocumentWrappers/:id", layout = MainView.class)
@ViewController("WebdavDocumentWrapper.detail")
@ViewDescriptor("webdav-document-wrapper-detail-view.xml")
@EditedEntityContainer("webdavDocumentWrapperDc")
public class WebdavDocumentWrapperDetailView extends StandardDetailView<WebdavDocumentWrapper> {
    // tag::webdavDocumentField[]
    @ViewComponent
    private WebdavDocumentUploadField webdavDocumentField;

    // end::webdavDocumentField[]

    // tag::init[]
    @Subscribe
    public void onInit(final InitEvent event) {
        // end::init[]
        // tag::setMaxFileSize[]
        webdavDocumentField.setMaxFileSize(5242880);
        // end::setMaxFileSize[]
        // tag::init[]
    }
    // end::init[]
}