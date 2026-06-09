package com.aitasker.controller;

import com.stripe.model.*;
import com.stripe.net.Webhook;
import com.aitasker.service.StripeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe/webhook")
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookController {

    private final StripeService stripeService;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    /**
     * POST /api/stripe/webhook
     * Xá»­ lĂ½ webhooks tá»« Stripe
     */
    @PostMapping
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        try {
            // XĂ¡c minh chá»¯ kĂ½ webhook
            Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

            log.info("Nháº­n webhook Stripe: {}", event.getType());

            // Xá»­ lĂ½ cĂ¡c sá»± kiá»‡n
            switch (event.getType()) {
                case "payment_intent.succeeded":
                    handlePaymentIntentSucceeded(event);
                    break;
                case "payment_intent.payment_failed":
                    handlePaymentIntentFailed(event);
                    break;
                case "charge.refunded":
                    handleChargeRefunded(event);
                    break;
                case "payout.paid":
                    handlePayoutPaid(event);
                    break;
                default:
                    log.info("Loáº¡i sá»± kiá»‡n chÆ°a Ä‘Æ°á»£c xá»­ lĂ½: {}", event.getType());
            }

            return ResponseEntity.ok("Webhook Ä‘Æ°á»£c nháº­n");

        } catch (Exception e) {
            log.error("Lá»—i webhook: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Lá»—i webhook: " + e.getMessage());
        }
    }

    /**
     * Xá»­ lĂ½ payment_intent.succeeded
     */
    private void handlePaymentIntentSucceeded(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject()
                .orElse(null);

        if (paymentIntent != null) {
            log.info("PaymentIntent thĂ nh cĂ´ng: {}", paymentIntent.getId());
            stripeService.confirmPaymentIntent(paymentIntent.getId(), "succeeded");
            
            // TODO: Láº¥y user + payment tá»« metadata Ä‘á»ƒ gá»­i notification
            // notificationEventService.notifyPaymentConfirmed(user, payment);
        }
    }

    /**
     * Xá»­ lĂ½ payment_intent.payment_failed
     */
    private void handlePaymentIntentFailed(Event event) {
        PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer()
                .getObject()
                .orElse(null);

        if (paymentIntent != null) {
            log.warn("PaymentIntent tháº¥t báº¡i: {}", paymentIntent.getId());
            // TODO: thĂ´ng bĂ¡o cho khĂ¡ch hĂ ng vá» lá»—i
        }
    }

    /**
     * Xá»­ lĂ½ charge.refunded
     */
    private void handleChargeRefunded(Event event) {
        Charge charge = (Charge) event.getDataObjectDeserializer()
                .getObject()
                .orElse(null);

        if (charge != null) {
            log.info("HĂ³a Ä‘Æ¡n Ä‘Æ°á»£c hoĂ n tiá»n: {}", charge.getId());
            // TODO: cáº­p nháº­t tráº¡ng thĂ¡i hoĂ n tiá»n
        }
    }

    /**
     * Xá»­ lĂ½ payout.paid
     */
    private void handlePayoutPaid(Event event) {
        Payout payout = (Payout) event.getDataObjectDeserializer()
                .getObject()
                .orElse(null);

        if (payout != null) {
            log.info("Thanh toĂ¡n Ä‘Æ°á»£c thá»±c hiá»‡n: {}", payout.getId());
            // TODO: thĂ´ng bĂ¡o cho chuyĂªn gia vá» thanh toĂ¡n
        }
    }
}

