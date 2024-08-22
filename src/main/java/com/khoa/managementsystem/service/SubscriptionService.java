package com.khoa.managementsystem.service;

import com.khoa.managementsystem.enums.PlanType;
import com.khoa.managementsystem.model.Subscription;
import com.khoa.managementsystem.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);

    Subscription getUserSubscription(Long userId);

    Subscription updateSubscription(Long userId, PlanType planType);

    boolean isValid(Subscription subscription);

}
