package com.khoa.managementsystem.payment.vnpay;

import com.khoa.managementsystem.enums.PlanType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.khoa.managementsystem.model.User;
import com.khoa.managementsystem.payment.response.PaymentLinkResponse;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VNPayService {

    @Value("${vnpay.website}")
    private String websiteCode;

    @Value("${vnpay.host}")
    private String hostUrl;

    @Value("${vnpay.api.secret}")
    private String apiSecret;

    private static final Logger logger = LoggerFactory.getLogger(VNPayService.class);

    public PaymentLinkResponse createPaymentLink(User user, int amount, PlanType planType) {
        Map<String, String> params = new HashMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_TmnCode", websiteCode);
        params.put("vnp_Amount", String.valueOf(amount * 100));
        params.put("vnp_Command", "pay");
        params.put("vnp_OrderInfo", "Payment for plan: " + planType);
        params.put("vnp_OrderType", "billpayment");
        params.put("vnp_ReturnUrl", encodeUrl(hostUrl + "/upgrade_plan/success?planType=" + planType));
        params.put("vnp_IpAddr", "127.0.0.1");
        params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        params.put("vnp_TxnRef", "TXN" + System.currentTimeMillis());

        logger.info("Payment Parameters: {}", params);

        // Build hash data
        String hashData = params.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        logger.info("Hash Data: {}", hashData);

        // Generate secure hash
        String vnpSecureHash = Hex.encodeHexString(HmacUtils.hmacSha512(apiSecret.getBytes(StandardCharsets.UTF_8), hashData.getBytes(StandardCharsets.UTF_8)));
        logger.info("Generated Secure Hash: {}", vnpSecureHash);

        params.put("vnp_SecureHash", vnpSecureHash);

        // Generate payment URL
        String paymentUrl = generatePaymentUrl(params);

        PaymentLinkResponse response = new PaymentLinkResponse();
        response.setPayment_link_url(paymentUrl);
        response.setPayment_link_id(params.get("vnp_TxnRef"));
        return response;
    }

    private String generatePaymentUrl(Map<String, String> params) {
        StringBuilder query = new StringBuilder(hostUrl).append("?");
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                query.append(URLEncoder.encode(entry.getKey(), StandardCharsets.US_ASCII.toString()))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), StandardCharsets.US_ASCII.toString()))
                        .append("&");
            }
            // Remove the last '&'
            query.setLength(query.length() - 1);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding URL parameters", e);
        }
        return query.toString();
    }

    private String encodeUrl(String url) {
        try {
            return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding URL", e);
        }
    }
}
