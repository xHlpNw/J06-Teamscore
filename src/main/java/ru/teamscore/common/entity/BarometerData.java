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
@Table(name = "barometer_data")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarometerData {
    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID sensorId;

    @Column(length = 32, nullable = false)
    private String deviceName;

    @Column(nullable = false)
    private LocalDateTime measuredAt;

    @Column(nullable = false)
    private double airPressure;
}
