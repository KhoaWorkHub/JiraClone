package com.khoa.managementsystem.controller.impl;

import com.khoa.managementsystem.controller.ISubscriptionController;
import com.khoa.managementsystem.enums.PlanType;
import com.khoa.managementsystem.model.Subscription;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.service.SubscriptionService;
import com.khoa.managementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubscriptionController extends BaseController implements ISubscriptionController {

    private final SubscriptionService subscriptionService;

    private final UserService userService;

    @Override
    public ResponseEntity<Object> getUserSubscription(String jwt) {
        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.getUserSubscription(user.getId());
        return ok(subscription);
    }

    @Override
    public ResponseEntity<Object> upgradeSubscription(String jwt, PlanType planType) {
        User user = userService.findUserProfileByJwt(jwt);
        Subscription subscription = subscriptionService.updateSubscription(user.getId(), planType);
        return ok(subscription);
    }
}
