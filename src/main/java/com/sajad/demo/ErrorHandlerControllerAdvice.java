//package com.sajad.demo;
//
//import com.sajad.demo.dto.ErrorResponseDto;
//import com.sajad.demo.exception.CommentNotAllowedException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestControllerAdvice
//public class ErrorHandlerControllerAdvice extends ResponseEntityExceptionHandler {
//
//    @ExceptionHandler(value = {IllegalArgumentException.class, CommentNotAllowedException.class})
//    protected ResponseEntity<Object> handleConflict(RuntimeException ex, HttpServletRequest request) {
//        String bodyOfResponse = "This should be application specific";
//
//        return handleExceptionInternal(ex, bodyOfResponse,
//                new HttpHeaders(), HttpStatus.CONFLICT, request);
//    }
//
////    @ExceptionHandler(Exception.class)
////    public ResponseEntity<ErrorResponseDto> handleError(Exception exception, Locale locale) {
////        exception = getUndeclaredException(exception);
////
////
////
////        ErrorResponseDto response = errorResponseResolver.resolve(exception, locale);
////
////        return ResponseEntity.status(response.getHttpStatus()).body(response);
////    }
////
////    private ErrorResponseDto createErrorDto(Exception exception){
////        ErrorResponseDto errorResponseDto = new ErrorResponseDto(exception.getCause().getsta)
////    }
//
//}
