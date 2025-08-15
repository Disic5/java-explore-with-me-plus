package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEventId(Long eventId);

    List<Comment> findAllByAuthorId(Long userId);

    @Modifying
    @Query("UPDATE Comment c SET c.text = :text WHERE c.id = :commentId")
    void updateCommentTextAndStatus(@Param("commentId") Long commentId, @Param("text") String text);
}
