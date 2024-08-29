package com.example.ratelimiter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<RateLimiterFilter> rateLimiterFilter(RateLimiter rateLimiter) {
        FilterRegistrationBean<RateLimiterFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RateLimiterFilter(rateLimiter));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
