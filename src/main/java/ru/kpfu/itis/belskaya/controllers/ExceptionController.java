package ru.kpfu.itis.belskaya.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.kpfu.itis.belskaya.exceptions.NotFoundException;
import ru.kpfu.itis.belskaya.models.dto.ExceptionDto;
import ru.kpfu.itis.belskaya.models.dto.ValidationErrorDto;
import ru.kpfu.itis.belskaya.models.dto.ValidationErrorsDto;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Elizaveta Belskaya
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpClientErrorException.class, IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object catchBadRequest(HttpServletRequest req, Exception exception) {
        String userAgent = req.getHeader("User-Agent");
        if (userAgent == null || !userAgent.contains("Mozilla")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ExceptionDto.builder()
                            .message(exception.getMessage())
                            .status(HttpStatus.BAD_REQUEST.value())
                            .build());
        }
        req.setAttribute("alert", "Sorry, your request was invalid" + exception.getMessage());
        return "/views/errorPage";
    }

    @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object catchNotFoundStatus(Exception exception, HttpServletRequest req) {
        String userAgent = req.getHeader("User-Agent");
        if (userAgent == null || !userAgent.contains("Mozilla")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ExceptionDto.builder()
                            .message(exception.getMessage())
                            .status(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        req.setAttribute("alert", "This page does not exist");
        return "/views/errorPage";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Object catchAccessDenied(AccessDeniedException ex, HttpServletRequest req) {
        String userAgent = req.getHeader("User-Agent");
        if (userAgent == null || !userAgent.contains("Mozilla")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ExceptionDto.builder()
                            .message(ex.getMessage())
                            .status(HttpStatus.FORBIDDEN.value())
                            .build());
        } else {
            throw ex;
        }
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String catchInternalErrorStatus(Throwable throwable) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Server Error</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Server Error</h1>\n" +
                "    <p>An error occurred on the server. Please try again later.</p>\n" +
                "</body>\n" +
                "</html>\n";
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ExceptionDto> handleResponseException(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(ExceptionDto.builder()
                        .message(ex.getMessage())
                        .status(ex.getStatus().value())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleMethodNotValid(HttpServletRequest req, MethodArgumentNotValidException ex) {
        String userAgent = req.getHeader("User-Agent");
        if (userAgent != null || userAgent.contains("Mozilla")) {
            req.setAttribute("alert", "Sorry, your request was invalid" + ex.getMessage());
            return "/views/errorPage";
        }
        List<ValidationErrorDto> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();

            String fieldName = null;
            String objectName = error.getObjectName();

            if (error instanceof FieldError) {
                fieldName = ((FieldError)error).getField();
            }
            ValidationErrorDto errorDto = ValidationErrorDto.builder()
                    .message(errorMessage)
                    .field(fieldName)
                    .object(objectName)
                    .build();

            errors.add(errorDto);
        });

        return ResponseEntity.badRequest().body(ValidationErrorsDto.builder()
                .errors(errors)
                .build());
    }

}
