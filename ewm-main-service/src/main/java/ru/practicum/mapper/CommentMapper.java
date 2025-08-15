package ru.practicum.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.User;

import java.time.format.DateTimeFormatter;

@Component
public class CommentMapper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Comment toComment(NewCommentDto newCommentDto, User author, Event event) {
        if (newCommentDto == null || author == null || event == null) {
            return null;
        }

        Comment comment = new Comment();
        comment.setAuthor(author);
        comment.setEvent(event);
        comment.setText(newCommentDto.getText());
        return comment;
    }

    public CommentDto toDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setCreated(
                comment.getCreated() != null
                        ? comment.getCreated().format(DATE_TIME_FORMATTER)
                        : null
        );
        dto.setEventId(comment.getEvent() != null ? comment.getEvent().getId() : null);
        dto.setAuthorId(comment.getAuthor() != null ? comment.getAuthor().getId() : null);
        dto.setStatus(comment.getStatus() != null ? comment.getStatus().name() : null);
        dto.setText(comment.getText());
        return dto;
    }
}