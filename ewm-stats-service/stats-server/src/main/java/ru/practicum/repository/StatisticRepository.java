package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.StatsDto;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.util.SqlQueries.findStatsByUrisAndTimestampBetween;
import static ru.practicum.util.SqlQueries.findUniqueStatsByUrisAndTimestampBetween;

public interface StatisticRepository extends JpaRepository<Hit, Long> {

    @Query(value = findUniqueStatsByUrisAndTimestampBetween)
    List<StatsDto> findUniqueStatsByUrisAndTimestampBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris
    );

    @Query(value = findStatsByUrisAndTimestampBetween)
    List<StatsDto> findStatsByUrisAndTimestampBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("uris") List<String> uris
    );
}
