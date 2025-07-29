package ru.practicum;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
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

    @NotNull
    private LocalDateTime timestamp;
}
