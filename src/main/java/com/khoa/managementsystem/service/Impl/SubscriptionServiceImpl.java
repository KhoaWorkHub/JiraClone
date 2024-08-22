package com.khoa.managementsystem.service.Impl;

import com.khoa.managementsystem.enums.PlanType;
import com.khoa.managementsystem.model.Subscription;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.repository.SubscriptionRepository;
import com.khoa.managementsystem.service.SubscriptionService;
import com.khoa.managementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final UserService userService;

    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();

        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if(!isValid(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription updateSubscription(Long userId, PlanType planType) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.setSubscriptionStartDate(LocalDate.now());
        if(planType.equals(PlanType.ANNUALLY)){
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        } else if(planType.equals(PlanType.MONTHLY)){
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
        }
//        if(planType.equals(PlanType.ANNUALLY)){
//            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
//        } else {
//            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
//        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if(subscription.getPlanType().equals(PlanType.FREE)){
            return true;
        }
        LocalDate endDate = subscription.getSubscriptionEndDate();
        LocalDate currentDate = LocalDate.now();

        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
