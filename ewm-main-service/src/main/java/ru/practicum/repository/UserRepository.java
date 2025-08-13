package ru.practicum.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.User;

import java.util.List;

import static ru.practicum.util.SqlQueries.findUsersByIds;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(findUsersByIds)
    List<User> findUsersByIds(List<Long> ids, Pageable pageable);
}
