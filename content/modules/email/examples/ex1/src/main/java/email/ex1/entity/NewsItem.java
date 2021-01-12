package email.ex1.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@JmixEntity
@Table(name = "SAMPLE_NEWS_ITEM")
@Entity(name = "sample_NewsItem")
public class NewsItem {
    @Id
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    private UUID id;

    @Column(name = "DATE_")
    private LocalDate date;

    @InstanceName
    @Column(name = "CAPTION")
    private String caption;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}