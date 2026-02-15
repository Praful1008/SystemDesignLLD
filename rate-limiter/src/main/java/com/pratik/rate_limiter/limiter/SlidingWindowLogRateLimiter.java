package com.pratik.rate_limiter.limiter;

import com.pratik.rate_limiter.entity.RateLimitConfig;
import com.pratik.rate_limiter.enums.RateLimitType;


import java.util.*;


public class SlidingWindowLogRateLimiter extends RateLimiter{

    private final Map<String, Queue<Long>> requestLog;

    public SlidingWindowLogRateLimiter(RateLimitConfig config) {
        super(config, RateLimitType.SLIDING_WINDOW_LOG);
        requestLog = new HashMap<>();
    }

    @Override
    public boolean allowRequest(String userId) {

        long now = java.time.Instant.now().toEpochMilli() / 1000;
        Queue<Long> log = requestLog.getOrDefault(userId, new ArrayDeque<>());

        while(!log.isEmpty() && (now - log.peek()) > config.getWindowInSeconds()){
            log.poll();
        }

        if(log.size() < config.getMaxRequests()){
            log.add(now);
            return true;
        }
        return false;
    }
}
