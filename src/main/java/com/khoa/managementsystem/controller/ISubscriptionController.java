package com.khoa.managementsystem.controller;

import com.khoa.managementsystem.enums.PlanType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/subscription")
public interface ISubscriptionController {


    @GetMapping("/user")
    ResponseEntity<Object> getUserSubscription(@RequestHeader("Authorization") String jwt
    );

    @PatchMapping("/upgrade")
    ResponseEntity<Object> upgradeSubscription(@RequestHeader("Authorization") String jwt,
                                               @RequestParam("planType") PlanType planType
    );

}
