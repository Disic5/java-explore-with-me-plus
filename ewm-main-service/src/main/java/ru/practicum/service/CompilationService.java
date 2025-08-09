package ru.practicum.service;

import ru.practicum.dto.CompilationDto;
import ru.practicum.dto.EventShortDto;
import ru.practicum.model.Compilation;
import ru.practicum.model.EventModel;
import ru.practicum.repository.CompilationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompilationService {
    private final CompilationRepository compilationRepository;

    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        return compilationRepository.findAll()
                .stream()
                .filter(comp -> pinned == null || comp.getPinned().equals(pinned))
                .skip(from)
                .limit(size)
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CompilationDto getCompilationById(Long compId) {
        return compilationRepository.findById(compId)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Compilation not found"));
    }

    private CompilationDto mapToDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                mapEventsToShortDto(compilation.getEvents())
        );
    }

    private List<EventShortDto> mapEventsToShortDto(List<EventModel> eventModels) {
        return eventModels.stream()
                .map(this::mapToEventShortDto)
                .collect(Collectors.toList());
    }

    private EventShortDto mapToEventShortDto(EventModel eventModel) {
        return new EventShortDto(
                eventModel.getId(),
                eventModel.getTitle(),
                eventModel.getAnnotation(),
                eventModel.getEventDate(),
                eventModel.getLocation(),
                eventModel.getPaid(),
                eventModel.getConfirmedRequests()
        );
    }
}