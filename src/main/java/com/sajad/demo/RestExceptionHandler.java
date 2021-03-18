package com.sajad.demo;

import com.sajad.demo.dto.ErrorResponseDto;
import com.sajad.demo.exception.CommentNotAllowedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CommentNotAllowedException.class)
    protected ResponseEntity<Object> handleEntityNotFound(CommentNotAllowedException ex) {

        ErrorResponseDto responseDto = new ErrorResponseDto(BAD_REQUEST, "Can't comment");

        return ResponseEntity.ok("Error.");
    }

}