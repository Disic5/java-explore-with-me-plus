package ru.practicum.controller.publicEndpoint;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.service.comment.CommentService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/events/{eventId}/comments")
@RequiredArgsConstructor
public class PublicCommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getCommentsByEvent(@PathVariable Long eventId) {
        log.info("Get comment by event id {}", eventId);
        return commentService.getCommentsByEvent(eventId);
    }
}
