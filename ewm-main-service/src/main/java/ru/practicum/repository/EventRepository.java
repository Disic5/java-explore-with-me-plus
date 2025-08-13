package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Category;
import ru.practicum.model.Event;
import ru.practicum.model.enums.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.practicum.util.SqlQueries.*;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(findAllByPublic)
    List<Event> findAllByPublic(
            String text,
            List<Long> categoryIds,
            Boolean paid,
            LocalDateTime start,
            LocalDateTime end,
            Boolean onlyAvailable,
            State state,
            Pageable pageable
    );

    @Query(findAllByFilter)
    List<Event> findAllByFilter(
            List<Long> userIds,
            List<String> states,
            List<Long> categoryIds,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable
    );

    @Query(findByUserId)
    List<Event> findByUserId(
            Long userId,
            Pageable pageable
    );

    @Query(findByInitiatorIdAndId)
    Optional<Event> findByInitiatorIdAndId(
            Long userId,
            Long eventId
    );

    List<Event> findByCategory(
            Category category
    );

    List<Event> findAllByIdIn(
            List<Long> ids
    );
}
