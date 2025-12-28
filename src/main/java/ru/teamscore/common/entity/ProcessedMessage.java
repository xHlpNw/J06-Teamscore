package ru.teamscore.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "processed_message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedMessage {
    @Id
    @Column(nullable = false)
    private UUID messageId;

    @Column(nullable = false)
    private LocalDateTime processedAt;
}
