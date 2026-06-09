package com.aitasker.controller;

import com.aitasker.dto.*;
import com.aitasker.service.StripeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
@RequiredArgsConstructor
public class PaymentIntentController {

    private final StripeService stripeService;

    /**
     * POST /api/stripe/payment-intent
     * Táº¡o PaymentIntent trĂªn Stripe cho thanh toĂ¡n Escrow
     */
    @PostMapping("/payment-intent")
    public ResponseEntity<PaymentIntentResponse> createPaymentIntent(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CreatePaymentIntentRequest request) {
        
        PaymentIntentResponse response = stripeService.createPaymentIntent(
                userDetails.getUsername(), 
                request
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * GET /api/stripe/payment-intent/{paymentIntentId}
     * Láº¥y tráº¡ng thĂ¡i cá»§a PaymentIntent
     */
    @GetMapping("/payment-intent/{paymentIntentId}")
    public ResponseEntity<PaymentStatusResponse> getPaymentStatus(
            @PathVariable String paymentIntentId,
            @AuthenticationPrincipal UserDetails userDetails) {
        
        String status = stripeService.getPaymentIntentStatus(paymentIntentId);
        
        PaymentStatusResponse response = PaymentStatusResponse.builder()
                .paymentIntentId(paymentIntentId)
                .status(status)
                .build();
        
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/stripe/confirm-payment
     * XĂ¡c nháº­n thanh toĂ¡n sau khi Stripe thĂ nh cĂ´ng
     */
    @PostMapping("/confirm-payment")
    public ResponseEntity<String> confirmPayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody ConfirmPaymentRequest request) {
        
        stripeService.confirmPaymentIntent(
                request.getPaymentIntentId(),
                "succeeded"
        );
        
        return ResponseEntity.ok("Thanh toĂ¡n Ä‘Æ°á»£c xĂ¡c nháº­n thĂ nh cĂ´ng");
    }

    /**
     * POST /api/stripe/refund
     * HoĂ n tiá»n thanh toĂ¡n
     */
    @PostMapping("/refund")
    public ResponseEntity<String> refundPayment(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody RefundPaymentRequest request) {
        
        stripeService.refundPayment(
                request.getPaymentId(),
                request.getReason()
        );
        
        return ResponseEntity.ok("Thanh toĂ¡n Ä‘Æ°á»£c hoĂ n tiá»n thĂ nh cĂ´ng");
    }

    /**
     * POST /api/stripe/payout
     * Xá»­ lĂ½ thanh toĂ¡n cho ChuyĂªn Gia
     */
    @PostMapping("/payout")
    public ResponseEntity<String> processPayout(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long paymentId) {
        
        stripeService.processExpertPayout(paymentId);
        
        return ResponseEntity.ok("Thanh toĂ¡n Ä‘Æ°á»£c xá»­ lĂ½ thĂ nh cĂ´ng");
    }
}

