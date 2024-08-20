package com.khoa.managementsystem.exception;

import com.khoa.managementsystem.controller.impl.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(value = {
            ResourceNotFoundException.class,
            RuntimeException.class,
            BusinessException.class
    })
    public ResponseEntity<?> badReq(RuntimeException e) {
        e.printStackTrace();
        if (e instanceof BusinessException) {
            BusinessException be = (BusinessException) e;
            return err(be.getMessage(), null, be.getError());
        }
        return err(e.getMessage(), null, ProjectExceptionEnum.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            Exception.class
    })
    public ResponseEntity<?> internalErr(Exception e) {
        return internalErr(e.getMessage(), e, null);
    }

    @ExceptionHandler(value = {
            Throwable.class
    })
    public ResponseEntity<?> throwable(Throwable throwable) {
        return internalErr(throwable.getMessage(), throwable, null);
    }
}
