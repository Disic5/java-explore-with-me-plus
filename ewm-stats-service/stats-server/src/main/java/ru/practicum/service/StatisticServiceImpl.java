package ru.practicum.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.HitDto;
import ru.practicum.StatsDto;
import ru.practicum.constant.StatisticConstant;
import ru.practicum.exception.ValidationException;
import ru.practicum.mappers.HitMapper;
import ru.practicum.model.Hit;
import ru.practicum.repository.StatisticRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static ru.practicum.mappers.HitMapper.toHitDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;


    @Override
    public HitDto createStatistic(HitDto hitDto) {
        log.debug("Creating hit: {}", hitDto);
        Hit savedHit = statisticRepository.save(HitMapper.toHit(hitDto));
        log.info("Created successfully hit: {}", savedHit);
        return toHitDto(savedHit);
    }

    @Override
    public Collection<StatsDto> getAllViewStatsDto(String start, String end, List<String> uris, Boolean unique) {
        if (start == null || end == null) {
            throw new ValidationException("Дата не может быть пустой");
        }
        log.debug("Getting statistics from {} to {}, unique: {}, for URIs: {}", start, end, unique, uris);

        LocalDateTime startTime = parseDate(start);
        LocalDateTime endTime = parseDate(end);

        if (startTime.isAfter(endTime)) {
            throw new ValidationException("Дата начала должна быть раньше даты окончания");
        }

        return unique
                ? statisticRepository.findUniqueStatsByUrisAndTimestampBetween(startTime, endTime, uris)
                : statisticRepository.findStatsByUrisAndTimestampBetween(startTime, endTime, uris);
    }

    private LocalDateTime parseDate(String date) {
        if (date == null || date.isBlank()) {
            throw new ValidationException("Дата не может быть пустой");
        }

        String decodedDate = URLDecoder.decode(date, StandardCharsets.UTF_8);

        List<String> patterns = Arrays.asList(
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd'T'HH:mm:ss",
                StatisticConstant.DATE_TIME_FORMATTER
        );

        for (String pattern : patterns) {
            try {
                return LocalDateTime.parse(decodedDate, DateTimeFormatter.ofPattern(pattern));
            } catch (DateTimeParseException ignored) {
            }
        }
        throw new IllegalArgumentException("Не удалось проанализировать дату: " + decodedDate);
    }
}
