package ru.practicum.util;

public interface SqlQueries {

    /**
     * EventRepository
     */
    String findAllByPublic = """
            SELECT e
            FROM Event as e
            WHERE (:text IS NULL OR (e.annotation ilike %:text% OR e.description ilike %:text%))
            AND (:categoryIds IS NULL OR e.category.id in :categoryIds)
            AND (:paid IS NULL OR e.paid = :paid)
            AND (e.eventDate >= :start)
            AND (e.state = :state)
            AND (:onlyAvailable = false OR e.participantLimit = 0 OR e.confirmedRequests <= e.participantLimit)
            AND (CAST(:end AS DATE) IS NULL OR e.eventDate <= :end)
            """;

    String findAllByFilter = """
            SELECT e
            FROM Event as e
            WHERE (:userIds IS NULL OR e.initiator.id in :userIds)
            AND (:states IS NULL OR e.state in :states)
            AND (:categoryIds IS NULL OR e.category.id in :categoryIds)
            AND (CAST(:start AS DATE) IS NULL OR e.eventDate >= :start)
            AND (CAST(:end AS DATE) IS NULL OR e.eventDate <= :end)
            """;

    String findByUserId = """
            SELECT e
            FROM Event as e
            WHERE e.initiator.id = :userId
            """;

    String findByInitiatorIdAndId = """
            SELECT e
            FROM Event as e
            WHERE e.id = :eventId
            AND e.initiator.id = :userId
            """;

    /**
     * UserRepository
     */
    String findUsersByIds = """
            SELECT u
            FROM User as u
            WHERE (:ids IS NULL OR u.id in :ids)
            """;
}
