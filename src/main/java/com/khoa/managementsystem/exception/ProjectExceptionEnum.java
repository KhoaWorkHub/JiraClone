package com.khoa.managementsystem.exception;


import lombok.Getter;


@Getter
public enum ProjectExceptionEnum {
    SUCCESS(0, "Success"),
    FAILED(1, "Failed"),
    BAD_REQUEST(400, "Bad Request" ),
    INTERNAL_SERVER_ERROR( 1001, "Internal server error"),

    //Todo USER
    USER_ALREADY_EXISTED(1002, "User email already existed"),
    USERNAME_NOT_FOUND(1003, "Username not found with email : %s"),
    PASSWORD_NOT_FOUND(1004, "Password not found"),
    USER_NOT_FOUND(1005, "User not found"),

    //Todo PROJECT
    PROJECT_NOT_FOUND(2001, "Project not found");










    private final int businessCode;
    private final String message;


     ProjectExceptionEnum(int businessCode, String message) {
        this.businessCode = businessCode;
        this.message = message;
    }

    public String formatMessage(Object... args) {
        return String.format(this.message, args);
    }
}
