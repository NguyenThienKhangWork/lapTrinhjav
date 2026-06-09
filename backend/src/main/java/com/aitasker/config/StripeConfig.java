package com.aitasker.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;

@Configuration
public class StripeConfig {

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Value("${stripe.public-key}")
    private String stripePublicKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    public String getStripePublicKey() {
        return stripePublicKey;
    }

    public String getStripeSecretKey() {
        return stripeSecretKey;
    }
}