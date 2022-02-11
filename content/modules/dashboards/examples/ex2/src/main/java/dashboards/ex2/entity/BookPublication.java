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
@Table(name = "SAMPLE_BOOK_PUBLICATION", indexes = {
        @Index(name = "IDX_BOOKPUBLICATION_BOOK_ID", columnList = "BOOK_ID"),
        @Index(name = "IDX_BOOKPUBLICATION", columnList = "PUBLISHER_ID"),
        @Index(name = "IDX_BOOKPUBLICATION_TOWN_ID", columnList = "TOWN_ID")
})
@Entity(name = "sample_BookPublication")
public class BookPublication {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "YEAR_")
    private Integer year;

    @InstanceName
    @JoinColumn(name = "BOOK_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @JoinColumn(name = "PUBLISHER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisher;

    @JoinColumn(name = "TOWN_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Town town;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public String getDeletedBy() {
        return deletedBy;
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

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @DeletedDate
    @Column(name = "DELETED_DATE")
    private Date deletedDate;

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}