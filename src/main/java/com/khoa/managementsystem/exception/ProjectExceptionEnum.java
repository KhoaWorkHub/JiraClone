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
    USER_NOT_FOUND_WITH_ID(1006, "User not found with id: %s"),

    //Todo PROJECT
    PROJECT_NOT_FOUND(2001, "Project not found"),

    //Todo INVITATION
    INVITATION_NOT_FOUND(3001, "Invitation not found"),

    //Todo ISSUE
    ISSUE_NOT_FOUND(4001, "Issue not found"),
    ISSUE_NOT_FOUND_WITH_ID(4002, "Issue not found with id: %s"),

    //Todo COMMENT
    COMMENT_NOT_FOUND(5001, "Comment not found"),
    COMMENT_NOT_FOUND_WITH_ID(5002, "Comment not found with id: %s"),
    COMMENT_NOT_BELONG_TO_USER(5003, "Comment not belong to user"),

    //Todo CHAT
    CHAT_NOT_FOUND(6001, "Chat not found");











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
