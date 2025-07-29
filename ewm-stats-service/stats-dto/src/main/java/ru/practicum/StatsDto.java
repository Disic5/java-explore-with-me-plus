package ru.practicum;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsDto {
    @NotBlank
    private String app;

    @NotBlank
    private String uri;  // Consider using 'uri' instead of 'url' for REST consistency

    @NotNull  // Should be @NotNull since it's a Long
    private Long hits;
}