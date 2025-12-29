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
@Table(name = "accelerometer_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccelerometerData {
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
    private double x;

    @Column(nullable = false)
    private double y;

    @Column(nullable = false)
    private double z;
}
