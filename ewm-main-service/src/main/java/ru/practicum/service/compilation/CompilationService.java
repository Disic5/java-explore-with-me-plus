package ru.practicum.service.compilation;

import ru.practicum.dto.compilation.CompilationDto;
import ru.practicum.dto.compilation.NewCompilationDto;
import ru.practicum.dto.compilation.UpdateCompilationRequest;

import java.util.Collection;

public interface CompilationService {

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    Collection<CompilationDto> getAllCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateRequest);

    void deleteCompilation(Long id);

    CompilationDto getCompilationById(Long compId);
}