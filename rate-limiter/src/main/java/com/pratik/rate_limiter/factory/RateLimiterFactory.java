package com.pratik.rate_limiter.factory;

import com.pratik.rate_limiter.entity.RateLimitConfig;
import com.pratik.rate_limiter.enums.RateLimitType;
import com.pratik.rate_limiter.limiter.RateLimiter;
import com.pratik.rate_limiter.limiter.SlidingWindowLogRateLimiter;
import com.pratik.rate_limiter.limiter.TockenBucketRateLimiter;

public class RateLimiterFactory {

    public static RateLimiter createRateLimiter(RateLimitType type, RateLimitConfig config){
        return switch (type) {
            case TOKEN_BUCKET -> new TockenBucketRateLimiter(config);
            case SLIDING_WINDOW_LOG -> new SlidingWindowLogRateLimiter(config);
            default -> throw new IllegalArgumentException("Unknown Algorithm (Type)");
        };
    }

}
