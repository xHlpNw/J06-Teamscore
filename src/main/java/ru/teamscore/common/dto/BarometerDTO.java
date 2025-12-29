package ru.teamscore.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BarometerDTO(
        @JsonProperty("air_pressure")
        double airPressure
) { }
