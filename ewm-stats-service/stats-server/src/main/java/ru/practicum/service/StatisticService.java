package ru.practicum.service;

import ru.practicum.HitDto;
import ru.practicum.StatsDto;

import java.util.Collection;
import java.util.List;

public interface StatisticService {

    HitDto createStatistic(
            HitDto hitDto
    );

    Collection<StatsDto> getAllViewStatsDto(
            String start,
            String end,
            List<String> uri,
            Boolean unique
    );
}