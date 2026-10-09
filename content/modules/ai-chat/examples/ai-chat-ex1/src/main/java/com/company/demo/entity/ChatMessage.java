package com.company.demo.entity;

import io.jmix.aichat.data.AiChatMessage;
import io.jmix.aichat.entity.AiMessageRole;
import io.jmix.core.FileRef;
import io.jmix.core.entity.FileRefConverter;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

// tag::entity[]
@JmixEntity
@Table(name = "CHAT_MESSAGE")
@Entity
public class ChatMessage implements AiChatMessage {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;

    @Column(name = "ROLE", nullable = false, length = 50)
    private String role;

    @Column(name = "CONTENT")
    @Lob
    private String content;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private OffsetDateTime createdDate;

    @ElementCollection
    @CollectionTable(name = "CHAT_MESSAGE_ATTACHMENTS",
            joinColumns = @JoinColumn(name = "CHAT_MESSAGE_ID"))
    @OrderColumn(name = "ATTACHMENTS_ORDER")
    @Convert(converter = FileRefConverter.class)
    @Column(name = "ATTACHMENTS", length = 1024)
    private List<FileRef> attachments;

    @Override
    public AiMessageRole getRole() {
        return role == null ? null : AiMessageRole.fromId(role);
    }

    public void setRole(AiMessageRole role) {
        this.role = role == null ? null : role.getId();
    }

    @Override
    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(@Nullable String content) {
        this.content = content;
    }

    @Override
    public @Nullable Instant getTime() {
        return createdDate == null ? null : createdDate.toInstant();
    }

    @Override
    public List<FileRef> getAttachments() {
        return attachments == null ? List.of() : attachments; // <1>
    }
    // end::entity[]

    public void setAttachments(List<FileRef> attachments) {
        this.attachments = attachments;
    }

    public @Nullable OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(@Nullable OffsetDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public @Nullable UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public @Nullable Integer getVersion() {
        return version;
    }

    public void setVersion(@Nullable Integer version) {
        this.version = version;
    }
}
