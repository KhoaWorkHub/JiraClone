package com.khoa.managementsystem.service;

public interface EmailService {

    void sendEmailWithToken(String userEmail, String link);
}
