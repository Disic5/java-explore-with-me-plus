package ru.practicum.controller.privateEndpoint;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.service.comment.CommentService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid NewCommentDto newCommentDto
    ) {
        log.info("Add comment for user {} event {}", userId, eventId);
        return commentService.addComment(userId, eventId, newCommentDto);
    }

    @PatchMapping("/comments/{commentId}")
    public CommentDto updateCommentDto(
            @PathVariable Long userId,
            @PathVariable Long commentId,
            @Valid @RequestBody NewCommentDto newCommentDto
    ) {
        log.info("Update comment for user {} event {}", userId, commentId);
        return commentService.updateCommentDto(userId, commentId, newCommentDto);
    }

    @GetMapping("/comments")
    public List<CommentDto> getCommentsByUser(
            @PathVariable Long userId
    ) {
        log.info("Get comments by user {}", userId);
        return commentService.getCommentsByUser(userId);
    }

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long userId,
            @PathVariable Long commentId
    ) {
        log.info("Delete comment for user {} event {}", userId, commentId);
        commentService.deleteComment(userId, commentId);
    }
}
