package com.pratik.rate_limiter.limiter;

import com.pratik.rate_limiter.entity.RateLimitConfig;
import com.pratik.rate_limiter.enums.RateLimitType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class RateLimiter {
    protected final RateLimitConfig config;
    protected final RateLimitType type;

    public abstract boolean allowRequest(String userId);

}
