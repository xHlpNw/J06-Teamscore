package ru.teamscore.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.teamscore.common.SensorType;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sensor_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SensorMessage {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID sensorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SensorType sensorType;

    @Column(length = 32, nullable = false)
    private String deviceName;

    @Column(nullable = false)
    private LocalDateTime measuredAt;

    @Column(nullable = false)
    private LocalDateTime savedAt;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String valueJson;
}
