package webdav.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.webdav.annotation.WebdavSupport;
import io.jmix.webdav.entity.WebdavDocument;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

// tag::entity-start[]
@JmixEntity
@Table(name = "SAMPLE_CONTRACT", indexes = {
        @Index(name = "IDX_CONTRACT_DOCUMENT_ID", columnList = "DOCUMENT_ID")
})
@Entity(name = "sample_Contract")
public class Contract {

    // end::entity-start[]
    // tag::attributes[]
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @NotNull
    @Column(name = "NUMBER_", nullable = false)
    private String number;

    // end::attributes[]
    // tag::webdav-document[]
    @JoinColumn(name = "DOCUMENT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private WebdavDocument document;

    // end::webdav-document[]
    // tag::webdav-document-versioning[]
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @WebdavSupport(versioning = false)
    @JoinColumn(name = "DOC_WITHOUT_VERSION_ID")
    private WebdavDocument docWithoutVersion;

    // end::webdav-document-versioning[]
    public WebdavDocument getDocWithoutVersion() {
        return docWithoutVersion;
    }

    public void setDocWithoutVersion(WebdavDocument docWithoutVersion) {
        this.docWithoutVersion = docWithoutVersion;
    }

    public WebdavDocument getDocument() {
        return document;
    }

    public void setDocument(WebdavDocument document) {
        this.document = document;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    // tag::entity-end[]
}
// end::entity-end[]