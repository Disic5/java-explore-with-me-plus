package ru.practicum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto {
    @NotBlank
    private String app;

    @NotBlank
    private String url;

    @NotNull
    private Long hits;
}