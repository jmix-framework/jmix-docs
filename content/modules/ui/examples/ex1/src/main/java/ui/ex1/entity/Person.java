package ui.ex1.entity;

import io.jmix.core.FileRef;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.PropertyDatatype;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

// tag::file-storage-upload-field[]
// tag::file-upload-field[]
// tag::date-field[]
@JmixEntity
@Table(name = "UIEX1_PERSON")
@Entity(name = "uiex1_Person")
public class Person {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "STATUS")
    private java.lang.Boolean status;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    // end::file-storage-upload-field[]
    // end::date-field[]
    @Column(name = "DOCUMENT")
    private byte[] document;

    // end::file-upload-field[]

    // tag::date-field[]
    @Column(name = "BIRTHDAY")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    // end::date-field[]

    //todo: Check why studio is generating @PropertyDatatype annotation. Works correctly without it.
    //@PropertyDatatype("fileRef")
    // tag::file-storage-upload-field[]
    @PropertyDatatype("fileRef")
    @Column(name = "IMAGE")
    private FileRef image;

    /*setters and getters*/
    // end::file-storage-upload-field[]
    public java.lang.Boolean getStatus() {
        return status;
    }

    public void setStatus(java.lang.Boolean status) {
        this.status = status;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

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
    // tag::date-field[]
}
// end::date-field[]
// end::file-storage-upload-field[]
// end::file-upload-field[]