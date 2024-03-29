package com.company.library.view.runreport;


import com.company.library.entity.BookPublication;
import com.company.library.entity.LiteratureType;
import com.company.library.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import io.jmix.localfs.LocalFileStorageProperties;
import io.jmix.reports.ReportImportExport;
import io.jmix.reports.entity.Report;
import io.jmix.reports.entity.ReportOutputType;
import io.jmix.reports.entity.ReportTemplate;
import io.jmix.reports.exception.ReportingException;
import io.jmix.reports.runner.ReportRunContext;
import io.jmix.reports.runner.ReportRunner;
import io.jmix.reports.util.ReportZipUtils;
import io.jmix.reports.yarg.reporting.ReportOutputDocument;
import io.jmix.reportsflowui.runner.ParametersDialogShowMode;
import io.jmix.reportsflowui.runner.UiReportRunContext;
import io.jmix.reportsflowui.runner.UiReportRunner;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@Route(value = "RunReportView", layout = MainView.class)
@ViewController("RunReportView")
@ViewDescriptor("run-report-view.xml")
public class RunReportView extends StandardView {

    @Autowired
    private DataManager dataManager;
    // tag::inject-report-runner[]
    @Autowired
    private ReportRunner reportRunner;
    
    // end::inject-report-runner[]
    @Autowired
    private ReportZipUtils reportZipUtils;
    @Autowired
    private Downloader downloader;
    @Autowired
    private UiReportRunner uiReportRunner;
    @Autowired
    private LocalFileStorageProperties localFileStorageProperties;
    @Autowired
    private ReportImportExport reportImportExport;

    private Report getReportByCode(String code){
        return dataManager.load(Report.class)
                .query("select r from report_Report r where r.code = :code1")
                .parameter("code1", code)
                .one();
    }
    @Subscribe("rrcBtn1")
    public void onRrcBtn1Click(ClickEvent<JmixButton> event) {
        Report report = getReportByCode("BOOKS");
        LiteratureType type = dataManager.load(LiteratureType.class)
                .query("select l from LiteratureType l where l.name = :name1")
                .parameter("name1", "Art")
                .one();
        // tag::report-run-context-v1[]
        ReportRunContext context = new ReportRunContext(report)
                .addParam("type", type)
                .setOutputNamePattern("Books");
        // end::report-run-context-v1[]
        ReportOutputDocument document = reportRunner.run(context);
        List<ReportOutputDocument> documents = new ArrayList<>();
        documents.add(document);
        // tag::report-zip-utils[]
        byte[] zipArchiveContent = reportZipUtils.createZipArchive(documents);
        downloader.download(zipArchiveContent, "Reports.zip", DownloadFormat.ZIP);
        // end::report-zip-utils[]
    }

    // tag::rrc-btn2-start[]
    @Subscribe("rrcBtn2")
    public void onRrcBtn2Click(ClickEvent<JmixButton> event) {
        // end::rrc-btn2-start[]
        Report report = getReportByCode("BOOKS");
        LiteratureType type = dataManager.load(LiteratureType.class)
                .query("select c from LiteratureType c where c.name = :name")
                .parameter("name", "Art")
                .one();

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("type", type);
        ReportTemplate template = dataManager.load(ReportTemplate.class)
                .query("select c from report_ReportTemplate c where c.code = :code and c.report = :report")
                .parameter("code", "DEFAULT")
                .parameter("report", report)
                .one();
        // tag::report-run-context-v2[]

        ReportRunContext context = new ReportRunContext(report)
                .setReportTemplate(template)
                .setOutputType(ReportOutputType.PDF)
                .setParams(paramsMap);
        // end::report-run-context-v2[]
        // tag::report-runner-v1[]
        ReportOutputDocument document = reportRunner
                .run(new ReportRunContext(report).setParams(paramsMap)); // <1>
        // end::report-runner-v1[]
        //ReportOutputDocument document = reportRunner.run(context);
        // tag::rrc-btn2-end[]
    }
    // end::rrc-btn2-end[]
    // tag::rr-btn1-start[]
    @Subscribe("rrBtn1")
    public void onRrBtn1Click(ClickEvent<JmixButton> event) {
    // end::rr-btn1-start[]
        LiteratureType type = dataManager.load(LiteratureType.class)
                .query("select c from LiteratureType c where c.name = :name")
                .parameter("name", "Art")
                .one();
        // tag::report-runner-v2[]
        ReportOutputDocument document = reportRunner.byReportCode("BOOKS") // <1>
                .addParam("type", type) // <2>
                .withTemplateCode("books-template") // <3>
                .run(); // <4>
        // end::report-runner-v2[]
        // tag::rr-btn1-end[]
    }
    // end::rr-btn1-end[]
    // tag::rr-btn2-start[]
    @Subscribe("rrBtn2")
    public void onRrBtn2Click(ClickEvent<JmixButton> event) {
        // end::rr-btn2-start[]
        Report report = getReportByCode("BOOKS");
        LiteratureType type = dataManager.load(LiteratureType.class)
                .query("select c from LiteratureType c where c.name = :name")
                .parameter("name", "Art")
                .one();
        // tag::report-runner-v3[]
        ReportOutputDocument document = reportRunner.byReportEntity(report)
                .addParam("type", type)
                .withOutputType(ReportOutputType.PDF) // <1>
                .withOutputNamePattern("Books") // <2>
                .run();
        // end::report-runner-v3[]
        // tag::get-content[]
        String documentName = document.getDocumentName();

        byte[] content = document.getContent();
        // end::get-content[]
        // tag::rr-btn2-end[]
    }
    // end::rr-btn2-end[]
    @Subscribe("urrBtn1")
    public void onUrrBtn1Click(ClickEvent<JmixButton> event) {
        Report report = getReportByCode("BOOK_COUNT");
        // tag::run-and-show[]
        uiReportRunner.runAndShow(new UiReportRunContext(report));
        // end::run-and-show[]
    }

    @Subscribe("urrBtn2")
    public void onUrrBtn2Click(final ClickEvent<JmixButton> event) {
        Report report = getReportByCode("BOOK_COUNT");
        // tag::in-background[]
        uiReportRunner.byReportEntity(report)
                .withParametersDialogShowMode(ParametersDialogShowMode.IF_REQUIRED)
                .inBackground(RunReportView.this)
                .runAndShow();
        // end::in-background[]
    }

    @Subscribe("urrBtn3")
    public void onUrrBtn3Click(ClickEvent<JmixButton> event) {
        Report report = getReportByCode("PUBL");
        List<BookPublication> publicationList = dataManager.load(BookPublication.class).all().list();
        // tag::run-multiple-reports[]
        uiReportRunner.byReportEntity(report)
                .withOutputType(ReportOutputType.PDF)
                .withTemplateCode("publication-template")
                .withParametersDialogShowMode(ParametersDialogShowMode.NO)
                .runMultipleReports("entity", publicationList);
        // end::run-multiple-reports[]
    }

    @Subscribe("importBtn")
    public void onImportBtnClick(ClickEvent<JmixButton> event) {
        File tempFile = new File(localFileStorageProperties
                .getStorageDir() + "Book count.zip");
        byte[] bytes;
        try {
            bytes = FileUtils.readFileToByteArray(tempFile);
        } catch (IOException e) {
            throw new ReportingException(format("Error while importing"), e);
        }
        reportImportExport.importReportsWithResult(bytes,null);
    }

}