package ui.ex1.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.PropertyDatatype;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

// tag::file-storage-upload-field[]
// tag::file-upload-field[]
@JmixEntity
@Table(name = "UIEX1_PERSON")
@Entity(name = "uiex1_Person")
public class Person {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private java.util.UUID id;

    @InstanceName
    @Column(name = "NAME", nullable = false, length = 63)
    @NotNull
    private String name;

    // end::file-storage-upload-field[]
    @Column(name = "DOCUMENT")
    private byte[] document;

    // end::file-upload-field[]

    // tag::file-storage-upload-field[]
    @PropertyDatatype("fileRef")
    @Column(name = "IMAGE")
    private FileRef image;
    // end::file-storage-upload-field[]

    public FileRef getImage() {
        return image;
    }

    public void setImage(FileRef image) {
        this.image = image;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.util.UUID getId() {
        return id;
    }

    public void setId(java.util.UUID id) {
        this.id = id;
    }
    // tag::file-storage-upload-field[]
    // tag::file-upload-field[]
}
// end::file-storage-upload-field[]
// end::file-upload-field[]