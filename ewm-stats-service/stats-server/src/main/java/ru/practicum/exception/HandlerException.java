package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class HandlerException {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingParams(MissingServletRequestParameterException e) {
        log.error("Отсутствует обязательный параметр запроса: {}", e.getMessage());
        return ErrorResponse.builder()
                .error("not found")
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notFound(final NotFoundException e) {
        log.warn("404 {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .error("not found")
                .message(e.getMessage())
                .build(), e.getHttpStatus());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationHandler(final ValidationException e) {
        log.warn("404 {}", e.getMessage());
        return ErrorResponse.builder()
                .error("not found")
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> badRequest(final BadRequestException e) {
        log.warn("400 {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .error("Bad request")
                .message(e.getMessage())
                .build(), e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValid(MethodArgumentNotValidException e) {
        log.warn("400 {}", e.getMessage());
        return new ResponseEntity<>(ErrorResponse.builder()
                .error(e.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(Exception e) {
        log.error("Internal server error occurred: {}", e.getMessage(), e);
        return ErrorResponse.builder()
                .error("Internal Server Error")
                .message("An unexpected error occurred. Please try again later.")
                .build();
    }
}
