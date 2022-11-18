package az.company.accounts.exception;

import lombok.Getter;

import static lombok.AccessLevel.PUBLIC;

public class NotFoundException extends RuntimeException{
    @Getter(PUBLIC)
    private final String code;

    public NotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }
}













