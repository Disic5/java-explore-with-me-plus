package ru.practicum.controller.adminEndpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.comment.CommentService;

@Slf4j
@RestController
@RequestMapping("/admin/{userId}")
@RequiredArgsConstructor
public class AdminCommentController {
    private final CommentService commentService;

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long userId,
            @PathVariable Long commentId
    ) {
        log.info("Delete comment for user {} event {}", userId, commentId);
        commentService.deleteCommentAdmin(userId, commentId);
    }
}
