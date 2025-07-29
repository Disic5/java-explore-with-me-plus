package ru.practicum.util;

public interface SqlQueries {
    String findUniqueStatsByUrisAndTimestampBetween = """
            SELECT new ru.practicum.StatsDto(h.app, h.uri, COUNT(DISTINCT h.ip) AS quantity)
            FROM Hit h
            WHERE h.timestamp BETWEEN :start AND :end
            AND (:uris IS NULL OR h.uri IN :uris)
            GROUP BY h.app, h.uri
            ORDER BY quantity DESC
            """;

    String findStatsByUrisAndTimestampBetween = """
            SELECT new ru.practicum.StatsDto(h.app, h.uri, COUNT(h.ip) AS quantity)
            FROM Hit h
            WHERE h.timestamp BETWEEN :start AND :end
            AND (:uris IS NULL OR h.uri IN :uris)
            GROUP BY h.app, h.uri
            ORDER BY quantity DESC
            """;
}
