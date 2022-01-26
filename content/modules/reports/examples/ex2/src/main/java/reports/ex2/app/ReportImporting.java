package reports.ex2.app;

import com.haulmont.yarg.exception.ReportingException;
import io.jmix.core.security.Authenticated;
import io.jmix.localfs.LocalFileStorageProperties;
import io.jmix.reports.ReportImportExport;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import static java.lang.String.format;

@Component
public class ReportImporting {
    @Autowired
    protected LocalFileStorageProperties localFileStorageProperties;
    @Autowired
    protected ReportImportExport reportImportExport;

    @Authenticated
    public void importReports() {
        importReport("Book count.zip");
        importReport("Publication details.zip");
        importReport("Report for entity Book.zip");
        importReport("Book items location.zip");
        importReport("Recently added book items.zip");
    }

    private void importReport(String reportName){
        File tempFile = new File(localFileStorageProperties
                .getStorageDir() + reportName);
        byte[] bytes;
        try {
            bytes = FileUtils.readFileToByteArray(tempFile);
        } catch (IOException e) {
            throw new ReportingException(format("Error while importing"), e);
        }
        reportImportExport.importReportsWithResult(bytes,null);
    }
}
