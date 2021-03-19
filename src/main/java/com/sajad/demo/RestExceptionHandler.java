package com.sajad.demo;

import com.sajad.demo.dto.ErrorResponseDto;
import com.sajad.demo.exception.CommentNotAllowedException;
import com.sajad.demo.exception.ResourceNotFoundException;
import com.sajad.demo.exception.VoteNotAllowedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.sajad.demo.helper.Constants.ErrorMessages.*;

/**
 * Rest exception handler to catch the exceptions and provide a more meaningful message
 * along with the proper status code.
 * Here, we used constant hardcoded messages, but in case of need, we can use i18n's internationalization
 * message sources.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommentNotAllowedException.class)
    protected ResponseEntity<Object> handleCommentNotAllowed() {
        ErrorResponseDto responseDto = new ErrorResponseDto(COMMENT_NOT_ALLOWED_MSG);

        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(VoteNotAllowedException.class)
    protected ResponseEntity<Object> handleVoteNotAllowed() {
        ErrorResponseDto responseDto = new ErrorResponseDto(RATE_NOT_ALLOWED_MSG);

        return ResponseEntity.badRequest().body(responseDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleNotFound() {
        ErrorResponseDto responseDto = new ErrorResponseDto(ENTITY_NOT_FOUND_MSG);

        return ResponseEntity.badRequest().body(responseDto);
    }

    // We'll need to catch other type of exceptions as well, e.g., validation and binding errors
}