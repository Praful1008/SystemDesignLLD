package com.pratik.rate_limiter.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.pratik.rate_limiter.entity.RateLimitConfig;
import com.pratik.rate_limiter.entity.User;
import com.pratik.rate_limiter.enums.RateLimitType;
import com.pratik.rate_limiter.enums.UserTier;
import com.pratik.rate_limiter.factory.RateLimiterFactory;
import com.pratik.rate_limiter.limiter.RateLimiter;


@Service
public class RateLimiterService {

    private final Map<UserTier, RateLimiter> rateLimiters = new HashMap<>();

    public RateLimiterService(){
        rateLimiters.put(UserTier.FREE,
            RateLimiterFactory.createRateLimiter(
                RateLimitType.TOKEN_BUCKET, new RateLimitConfig(10, 60)) // 6 requests in 60 seconds
        );

        rateLimiters.put(UserTier.PREMIUM,
            RateLimiterFactory.createRateLimiter(
                RateLimitType.SLIDING_WINDOW_LOG, new RateLimitConfig(100, 60)) // 100 requests in 60 seconds
        );
    }

    public boolean allowRequest(User user){
        RateLimiter limiter = rateLimiters.get(user.getTier());
        if(limiter == null)
            throw new IllegalArgumentException("No limiter configured");

        return limiter.allowRequest(user.getUserId());
    }
}

