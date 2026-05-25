package dashboards.ex2.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "SAMPLE_BOOK_INSTANCE", indexes = {
        @Index(name = "IDX_BOOKINSTANCE", columnList = "BOOK_PUBLICATION_ID"),
        @Index(name = "IDX_BOOKINSTANCE", columnList = "LIBRARY_DEPARTMENT_ID")
})
@Entity(name = "sample_BookInstance")
public class BookInstance {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "IS_REFERENCE")
    private Boolean isReference;

    @Column(name = "INVENTORY_NUMBER")
    private Long inventoryNumber;

    @Column(name = "BOOK_COUNT")
    private Integer bookCount;

    @InstanceName
    @JoinColumn(name = "BOOK_PUBLICATION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private BookPublication bookPublication;

    @JoinColumn(name = "LIBRARY_DEPARTMENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private LibraryDepartment libraryDepartment;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @DeletedDate
    @Column(name = "DELETED_DATE")
    private Date deletedDate;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public LibraryDepartment getLibraryDepartment() {
        return libraryDepartment;
    }

    public void setLibraryDepartment(LibraryDepartment libraryDepartment) {
        this.libraryDepartment = libraryDepartment;
    }

    public BookPublication getBookPublication() {
        return bookPublication;
    }

    public void setBookPublication(BookPublication bookPublication) {
        this.bookPublication = bookPublication;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public Boolean getReference() {
        return isReference;
    }

    public void setReference(Boolean reference) {
        isReference = reference;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public Long getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(Long inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public Boolean getIsReference() {
        return isReference;
    }

    public void setIsReference(Boolean isReference) {
        this.isReference = isReference;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}