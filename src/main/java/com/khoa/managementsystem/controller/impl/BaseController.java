package com.khoa.managementsystem.controller.impl;

import com.khoa.managementsystem.controller.IBaseController;
import com.khoa.managementsystem.exception.ProjectExceptionEnum;
import com.khoa.managementsystem.response.BaseResponse;
import com.khoa.managementsystem.response.Meta;
import com.khoa.managementsystem.util.UUIDUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BaseController implements IBaseController {

    @Override
    public ResponseEntity<Object> ok(Object payload) {
        String requestId = UUIDUtil.generateUUID();
        BaseResponse response = new BaseResponse(new Meta(requestId, 200, "success", HttpStatus.OK.toString()), payload);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Object> err(String message, String requestId, ProjectExceptionEnum code) {
        BaseResponse response = new BaseResponse(new Meta(requestId, 400, code.getMessage(), HttpStatus.BAD_REQUEST.toString()), code.getBusinessCode(), message);
        return ResponseEntity.badRequest().body(response);
    }

    @Override
    public ResponseEntity<Object> internalErr(String message, Throwable throwable, String requestId) {
        BaseResponse response = new BaseResponse(new Meta(requestId, 500, "internal server error", HttpStatus.INTERNAL_SERVER_ERROR.toString()), message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(throwable.getClass().getName() + ": " + throwable.getMessage());
    }

    @Override
    public ResponseEntity<?> noContent() {
        return ResponseEntity.noContent().build();
    }
}

