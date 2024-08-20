package com.khoa.managementsystem.controller;

import com.khoa.managementsystem.exception.ProjectExceptionEnum;
import org.springframework.http.ResponseEntity;

public interface IBaseController {
    ResponseEntity<Object> ok(Object payload);
    ResponseEntity<Object> err(String message, String requestId, ProjectExceptionEnum code);
    ResponseEntity<Object> internalErr(String message, Throwable throwable, String requestId);
    ResponseEntity<?> noContent();
}