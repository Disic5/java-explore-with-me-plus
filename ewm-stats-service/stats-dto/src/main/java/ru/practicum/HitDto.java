package ru.practicum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String uri;
    private String ip;
    private LocalDateTime timeStamp;
}
