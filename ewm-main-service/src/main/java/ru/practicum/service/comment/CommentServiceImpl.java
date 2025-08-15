package ru.practicum.service.comment;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.comment.CommentDto;
import ru.practicum.dto.comment.NewCommentDto;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.ValidationException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.Comment;
import ru.practicum.model.Event;
import ru.practicum.model.User;
import ru.practicum.repository.CommentRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        User author = findUserOrThrow(userId);
        Event event = findEventOrThrow(eventId);
        Comment comment = createComment(newCommentDto, author, event);
        return saveAndConvertToDto(comment);
    }

    @Transactional
    @Override
    public CommentDto updateCommentDto(Long userId, Long commentId, NewCommentDto newCommentDto) {
        findUserOrThrow(userId);

        CommentDto commentDto = findCommentById(commentId);
        checkIsAuthor(userId, commentDto.getAuthor().getId());
        commentRepository.updateCommentTextAndStatus(commentId, newCommentDto.getText());

        entityManager.clear();

        return findCommentById(commentId);
    }

    @Override
    public List<CommentDto> getCommentsByEvent(Long eventId) {
        return commentRepository.findAllByEventId(eventId).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getCommentsByUser(Long userId) {
        return commentRepository.findAllByAuthorId(userId).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        findUserOrThrow(userId);
        Comment comment = findCommentOrThrow(commentId);
        validateCommentOwnership(comment, userId);
        deleteComment(comment);
    }

    @Override
    @Transactional
    public void deleteCommentAdmin(Long userId, Long commentId) {
        findUserOrThrow(userId);
        Comment comment = findCommentOrThrow(commentId);
        deleteComment(comment);
    }

    // Вспомогательные методы
    private void checkIsAuthor(Long userId, Long commentAuthorId) {

        if (!Objects.equals(userId, commentAuthorId)) {
            throw new ConflictException(String.format("Пользователь с идентификатором %s не является автором комментария", userId));
        }
    }

    private CommentDto findCommentById(Long commentId) {

        return CommentMapper.toCommentDto(commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(String.format("Comment with id %s not found", commentId))));
    }

    private User findUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + userId + " не найден"));
    }

    private Event findEventOrThrow(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id=" + eventId + " не найдено"));
    }

    private Comment createComment(NewCommentDto newCommentDto, User author, Event event) {
        Comment comment = CommentMapper.toComment(newCommentDto);
        comment.setAuthor(author);
        comment.setEvent(event);
        comment.setCreatedOn(LocalDateTime.now());
        return comment;
    }

    private CommentDto saveAndConvertToDto(Comment comment) {
        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.toCommentDto(savedComment);
    }

    private Comment findCommentOrThrow(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Комментарий с id=" + commentId + " не найден"));
    }

    private void validateCommentOwnership(Comment comment, Long userId) {
        if (!isCommentOwner(comment, userId)) {
            throw new ValidationException(
                    "Пользователь с id=" + userId + " не является автором комментария с id=" + comment.getId()
            );
        }
    }

    private boolean isCommentOwner(Comment comment, Long userId) {
        return comment.getAuthor().getId().equals(userId);
    }

    private void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }
}