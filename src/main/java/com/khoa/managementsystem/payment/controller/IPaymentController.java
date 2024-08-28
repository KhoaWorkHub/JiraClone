package com.khoa.managementsystem.payment.controller;

import com.khoa.managementsystem.enums.PlanType;
import com.khoa.managementsystem.payment.response.PaymentLinkResponse;
import com.razorpay.RazorpayException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api/payment")
public interface IPaymentController {

    @PostMapping("/{planType}")
    ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable PlanType planType,
                                                          @RequestHeader("Authorization") String jwt,
                                                          @RequestHeader("Payment-Method") String paymentMethod

    ) throws RazorpayException;
}
