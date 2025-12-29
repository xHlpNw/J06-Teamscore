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
@Table(name = "location_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationData {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, name = "sensor_id")
    private UUID sensorId;

    @Column(length = 32, nullable = false, name = "device_name")
    private String deviceName;

    @Column(nullable = false, name = "measured_at")
    private LocalDateTime measuredAt;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;
}
