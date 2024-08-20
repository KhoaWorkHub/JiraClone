package com.khoa.managementsystem.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{
    private ProjectExceptionEnum error;

    public BusinessException (ProjectExceptionEnum error) {
        super(error.getMessage());
        this.error = error;
    }

    public BusinessException (ProjectExceptionEnum error, Object... args) {
        super(error.formatMessage(args));
        this.error = error;
    }
}
