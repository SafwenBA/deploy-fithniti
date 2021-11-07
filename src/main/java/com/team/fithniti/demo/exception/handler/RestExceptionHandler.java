package com.team.fithniti.demo.exception.handler;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceExists;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.exception.handler.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidResource.class)
    public ResponseEntity<?> handleException(InvalidResource exception) {
        final HttpStatus notFound = HttpStatus.BAD_REQUEST;
        final ErrorDTO errorDto = ErrorDTO.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(notFound.value())
                .message(exception.getErrors().toString())
                .build();
        return new ResponseEntity<>(errorDto, notFound);
    }

    @ExceptionHandler(ResourceExists.class)
    public ResponseEntity<?> handleException(ResourceExists exception) {
        final HttpStatus foundCode = HttpStatus.FOUND;
        final ErrorDTO errorDto = ErrorDTO.builder()
                .errorCode(exception.getErrorCode())
                .httpCode(foundCode.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, foundCode);

    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<?> handleException(ResourceNotFound exception) {
        final HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        final ErrorDTO errorDto = ErrorDTO.builder()
                .httpCode(badRequest.value())
                .message(exception.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, badRequest);
    }
}
