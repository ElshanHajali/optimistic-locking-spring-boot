package az.company.accounts.exception.handler;

import az.company.accounts.dto.response.ExceptionResponse;
import az.company.accounts.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse exceptionResponse(NotFoundException exception) {
        log.error("NotFoundException: required object could not be found!");
        return ExceptionResponse.builder()
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
    }
}
