package ru.practicum.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private String title;
    private List<EventShortDto> events;
}