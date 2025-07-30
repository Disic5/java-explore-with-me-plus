package ru.practicum.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.practicum.HitDto;
import ru.practicum.StatsDto;
import ru.practicum.service.StatisticService;

import java.util.Collection;
import java.util.List;

import static ru.practicum.constant.StatisticConstant.DATE_TIME_FORMATTER;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatisticController {
    private final StatisticService statisticService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public HitDto createHit(@Valid @RequestBody HitDto hitDto) {
        log.info("Creating hit {} in the service", hitDto);
        return statisticService.createStatistic(hitDto);
    }

    @GetMapping("/stats")
    public Collection<StatsDto> getViewStatsDto(
            @RequestParam @NotNull @DateTimeFormat(pattern = DATE_TIME_FORMATTER) String start,
            @RequestParam @NotNull @DateTimeFormat(pattern = DATE_TIME_FORMATTER) String end,
            @RequestParam(required = false) List<String> uris,
            @RequestParam(defaultValue = "false") Boolean unique
    ) {
        log.info("Getting stats from parameters: start={}; end={}; uris={}; unique={}", start, end, uris, unique);
        return statisticService.getAllViewStatsDto(start, end, uris, unique);
    }
}
