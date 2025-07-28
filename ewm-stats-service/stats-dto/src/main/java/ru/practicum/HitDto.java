package ru.practicum;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HitDto {
    private Long id;

    @NotBlank
    @NotNull
    private String app;

    @NotBlank
    @NotNull
    private String url;

    private String ip;

    @NotNull  // Consider adding if this field should never be null
    private LocalDateTime timestamp;  // Changed to lowercase for consistency
}
