package com.viksy.mobilele.config;

import com.viksy.mobilele.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        return super.createExpressionHandler();
    }

    @Bean
    public MobileleMethodSecurityExpressionHandler mobileleMethodSecurityExpressionHandler(OfferService offerService) {
        return new MobileleMethodSecurityExpressionHandler(offerService);
    }
}
