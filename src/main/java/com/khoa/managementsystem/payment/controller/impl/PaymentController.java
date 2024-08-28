package com.khoa.managementsystem.payment.controller.impl;

import com.khoa.managementsystem.enums.PlanType;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.payment.controller.IPaymentController;
import com.khoa.managementsystem.payment.response.PaymentLinkResponse;
import com.khoa.managementsystem.payment.vnpay.VNPayService;
import com.khoa.managementsystem.service.UserService;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController implements IPaymentController {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    private final UserService userService;
    private final VNPayService vnPayService;

    @Override
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(PlanType planType, @RequestHeader("Authorization") String jwt, @RequestHeader("Payment-Method") String paymentMethod) throws RazorpayException {
        User user = userService.findUserProfileByJwt(jwt);
        int amount = 799 * 100;
        if (planType.equals(PlanType.ANNUALLY)) {
            amount = amount * 12;
            amount = (int) (amount * 0.7);
        }

        if ("VNPAY".equalsIgnoreCase(paymentMethod)) {
            PaymentLinkResponse response = vnPayService.createPaymentLink(user, amount, PlanType.valueOf(planType.name()));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else if ("RAZORPAY".equalsIgnoreCase(paymentMethod)) {
            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");

            JSONObject customer = new JSONObject();
            customer.put("name", user.getFullName());
            customer.put("email", user.getEmail());
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url", "https://localhost:5173/upgrade_plan/success?planType=" + planType);

            PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkId = payment.get("id");
            String paymentLinkUrl = payment.get("short_url");

            PaymentLinkResponse res = new PaymentLinkResponse();
            res.setPayment_link_url(paymentLinkUrl);
            res.setPayment_link_id(paymentLinkId);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}