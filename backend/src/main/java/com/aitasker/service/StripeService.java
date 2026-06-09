package com.aitasker.service;

import com.stripe.exception.*;
import com.stripe.model.*;
import com.stripe.param.*;
import com.aitasker.dto.*;
import com.aitasker.entity.*;
import com.aitasker.enums.PaymentStatus;
import com.aitasker.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeService {

    private final PaymentRepository paymentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    /**
     * Táº¡o Stripe PaymentIntent cho thanh toĂ¡n Escrow
     */
    @Transactional
    public PaymentIntentResponse createPaymentIntent(String clientEmail, CreatePaymentIntentRequest request) {
        try {
            log.info("Táº¡o Stripe PaymentIntent cho khĂ¡ch hĂ ng: {}", clientEmail);

            // Kiá»ƒm tra Project tá»“n táº¡i
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("KhĂ´ng tĂ¬m tháº¥y Dá»± Ăn"));

            // Kiá»ƒm tra KhĂ¡ch HĂ ng lĂ  chá»§ sá»Ÿ há»¯u
            if (!project.getClient().getEmail().equals(clientEmail)) {
                throw new RuntimeException("KhĂ´ng Ä‘Æ°á»£c phĂ©p: Chá»‰ khĂ¡ch hĂ ng dá»± Ă¡n má»›i cĂ³ thá»ƒ thanh toĂ¡n");
            }

            // Táº¡o PaymentIntent trĂªn Stripe
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (request.getAmount() * 100)) // Chuyá»ƒn Ä‘á»•i sang Ä‘Æ¡n vá»‹ xu
                    .setCurrency("vnd")
                    .setDescription("Thanh ToĂ¡n Escrow cho Dá»± Ăn #" + project.getId())
                    .putMetadata("projectId", project.getId().toString())
                    .putMetadata("milestoneId", request.getMilestoneId() != null ? 
                            request.getMilestoneId().toString() : "khĂ´ng")
                    .putMetadata("clientEmail", clientEmail)
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            log.info("PaymentIntent Ä‘Æ°á»£c táº¡o: {}", paymentIntent.getId());

            // LÆ°u báº£n ghi thanh toĂ¡n cá»¥c bá»™ (ESCROWED)
            Payment payment = Payment.builder()
                    .project(project)
                    .amount(request.getAmount())
                    .status(PaymentStatus.ESCROWED)
                    .paymentMethod("STRIPE_CARD")
                    .escrowStatus("HELD")
                    .build();

            if (request.getMilestoneId() != null) {
                // TODO: thĂªm cá»™t má»‘c
            }

            paymentRepository.save(payment);

            return PaymentIntentResponse.builder()
                    .clientSecret(paymentIntent.getClientSecret())
                    .paymentIntentId(paymentIntent.getId())
                    .amount(request.getAmount())
                    .currency("VND")
                    .status("CREATED")
                    .build();

        } catch (StripeException e) {
            log.error("Lá»—i Stripe: {}", e.getMessage());
            throw new RuntimeException("Thanh toĂ¡n Stripe khĂ´ng thĂ nh cĂ´ng: " + e.getMessage());
        }
    }

    /**
     * XĂ¡c nháº­n thanh toĂ¡n sau khi Stripe webhook thĂ´ng bĂ¡o thĂ nh cĂ´ng
     */
    @Transactional
    public void confirmPaymentIntent(String paymentIntentId, String status) {
        try {
            log.info("XĂ¡c nháº­n PaymentIntent: {} vá»›i tráº¡ng thĂ¡i: {}", paymentIntentId, status);

            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            if ("succeeded".equals(status)) {
                // Cáº­p nháº­t báº£n ghi thanh toĂ¡n thĂ nh CONFIRMED
                // TODO: cáº­p nháº­t tráº¡ng thĂ¡i trong cÆ¡ sá»Ÿ dá»¯ liá»‡u
                log.info("Thanh toĂ¡n Ä‘Æ°á»£c xĂ¡c nháº­n thĂ nh cĂ´ng: {}", paymentIntentId);
            } else if ("processing".equals(status)) {
                log.info("Thanh toĂ¡n Ä‘ang Ä‘Æ°á»£c xá»­ lĂ½: {}", paymentIntentId);
            } else if ("requires_payment_method".equals(status)) {
                log.warn("Thanh toĂ¡n yĂªu cáº§u phÆ°Æ¡ng thá»©c thanh toĂ¡n: {}", paymentIntentId);
            }

        } catch (StripeException e) {
            log.error("Lá»—i xĂ¡c nháº­n thanh toĂ¡n: {}", e.getMessage());
            throw new RuntimeException("XĂ¡c nháº­n thanh toĂ¡n khĂ´ng thĂ nh cĂ´ng: " + e.getMessage());
        }
    }

    /**
     * HoĂ n tiá»n náº¿u khĂ¡ch hĂ ng tá»« chá»‘i cá»™t má»‘c
     */
    @Transactional
    public void refundPayment(Long paymentId, String reason) {
        try {
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new RuntimeException("KhĂ´ng tĂ¬m tháº¥y Thanh ToĂ¡n"));

            if (payment.getStatus() != PaymentStatus.ESCROWED) {
                throw new RuntimeException("Chá»‰ nhá»¯ng khoáº£n thanh toĂ¡n ESCROWED má»›i cĂ³ thá»ƒ Ä‘Æ°á»£c hoĂ n tiá»n");
            }

            // TODO: xá»­ lĂ½ hoĂ n tiá»n trĂªn Stripe
            // Náº¿u chĂºng ta cĂ³ paymentIntentId Ä‘Æ°á»£c lÆ°u, chĂºng ta cĂ³ thá»ƒ hoĂ n tiá»n

            // Cáº­p nháº­t tráº¡ng thĂ¡i cá»¥c bá»™
            payment.setStatus(PaymentStatus.REFUNDED);
            payment.setEscrowStatus("REFUNDED");
            paymentRepository.save(payment);

            log.info("Thanh toĂ¡n Ä‘Æ°á»£c hoĂ n tiá»n: {} vá»›i lĂ½ do: {}", paymentId, reason);

        } catch (Exception e) {
            log.error("HoĂ n tiá»n khĂ´ng thĂ nh cĂ´ng: {}", e.getMessage());
            throw new RuntimeException("HoĂ n tiá»n khĂ´ng thĂ nh cĂ´ng: " + e.getMessage());
        }
    }

    /**
     * Xá»­ lĂ½ thanh toĂ¡n cho ChuyĂªn Gia
     */
    @Transactional
    public void processExpertPayout(Long paymentId) {
        try {
            Payment payment = paymentRepository.findById(paymentId)
                    .orElseThrow(() -> new RuntimeException("KhĂ´ng tĂ¬m tháº¥y Thanh ToĂ¡n"));

            if (payment.getStatus() != PaymentStatus.RELEASED) {
                throw new RuntimeException("Thanh toĂ¡n pháº£i Ä‘Æ°á»£c RELEASED trÆ°á»›c khi thanh toĂ¡n");
            }

            User expert = payment.getProject().getExpert();

            // Táº¡o Stripe Payout
            PayoutCreateParams params = PayoutCreateParams.builder()
                    .setAmount((long) (payment.getAmount() * 100))
                    .setCurrency("vnd")
                    .setDescription("Thanh ToĂ¡n cho Dá»± Ăn #" + payment.getProject().getId())
                    .putMetadata("expertId", expert.getId().toString())
                    .putMetadata("paymentId", paymentId.toString())
                    .build();

            Payout payout = Payout.create(params);

            log.info("Thanh toĂ¡n Ä‘Æ°á»£c táº¡o cho chuyĂªn gia {}: {}", expert.getId(), payout.getId());

            // TODO: lÆ°u tham chiáº¿u thanh toĂ¡n trong cÆ¡ sá»Ÿ dá»¯ liá»‡u

        } catch (StripeException e) {
            log.error("Lá»—i Thanh ToĂ¡n: {}", e.getMessage());
            throw new RuntimeException("Thanh toĂ¡n khĂ´ng thĂ nh cĂ´ng: " + e.getMessage());
        }
    }

    /**
     * Láº¥y tráº¡ng thĂ¡i PaymentIntent tá»« Stripe
     */
    public String getPaymentIntentStatus(String paymentIntentId) {
        try {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            return paymentIntent.getStatus();
        } catch (StripeException e) {
            log.error("Lá»—i láº¥y tráº¡ng thĂ¡i thanh toĂ¡n: {}", e.getMessage());
            return "lá»—i";
        }
    }

    /**
     * TĂ­nh phĂ­ Stripe (2.9% + 0.30 cho má»—i giao dá»‹ch)
     */
    public double calculateStripeFee(double amount) {
        return (amount * 0.029) + 0.30;
    }

    /**
     * Äá»‹nh dáº¡ng sá»‘ tiá»n cho Stripe (Ä‘Æ¡n vá»‹ xu)
     */
    public long formatAmountForStripe(double amount) {
        return Math.round(amount * 100);
    }
}

