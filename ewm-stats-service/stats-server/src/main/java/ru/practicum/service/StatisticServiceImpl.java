package ru.practicum.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.constant.StatisticConstant;
import ru.practicum.HitDto;
import ru.practicum.StatsDto;
import ru.practicum.mappers.HitMapper;
import ru.practicum.model.Hit;
import ru.practicum.repository.StatisticRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;

import static ru.practicum.mappers.HitMapper.toHitDto;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;

    @Transactional
    @Override
    public HitDto createStatistic(HitDto hitDto) {
        log.debug("Creating hit: {}", hitDto);
        Hit savedHit = statisticRepository.save(HitMapper.toHit(hitDto));
        log.info("Created successfully hit: {}", savedHit);
        return toHitDto(savedHit);
    }

    @Override
    public Collection<StatsDto> getAllViewStatsDto(String start, String end, List<String> uris, Boolean unique) {
        log.debug("Getting statistics from {} to {}, unique: {}, for URIs: {}", start, end, unique, uris);

        LocalDateTime startTime = parseDate(start);
        LocalDateTime endTime = parseDate(end);
        if (startTime.isAfter(endTime)) throw new ValidationException("Start date should be before end");

        return unique
                ? statisticRepository.findUniqueStatsByUrisAndTimestampBetween(startTime, endTime, uris)
                : statisticRepository.findStatsByUrisAndTimestampBetween(startTime, endTime, uris);
    }

    private LocalDateTime parseDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(StatisticConstant.DATE_TIME_FORMATTER);
            return LocalDateTime.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: " + StatisticConstant.DATE_TIME_FORMATTER, e);
        }
    }
}
