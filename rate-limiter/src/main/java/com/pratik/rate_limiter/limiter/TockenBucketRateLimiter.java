package com.pratik.rate_limiter.limiter;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import com.pratik.rate_limiter.entity.RateLimitConfig;
import com.pratik.rate_limiter.enums.RateLimitType;

public class TockenBucketRateLimiter extends RateLimiter{

    public TockenBucketRateLimiter(RateLimitConfig config) {
        super(config, RateLimitType.TOKEN_BUCKET);
        this.refillRate = (double)config.getWindowInSeconds() / config.getMaxRequests();
    }

    private final Map<String, Integer> tokens = new HashMap<>();
    private final Map<String, Long> lastRefillTime= new HashMap<>();
    private final double refillRate;


    @Override
    public boolean allowRequest(String userId) {
        
        int currentTokens = this.refillTokens(userId);
        if(currentTokens > 0){
            tokens.put(userId, currentTokens - 1);
            return true;
        }
        return false;
    }

    private int refillTokens(String userId){
        
        long now = Instant.now().toEpochMilli();
        long lastRefill = lastRefillTime.getOrDefault(userId, now);
        long elapsedSeconds = (now - lastRefill) / 1000;
        int tokensTORefill = (int)(elapsedSeconds / this.refillRate);

        Integer currentTokens = this.tokens.getOrDefault(userId, this.config.getMaxRequests());
        currentTokens = Math.min(config.getMaxRequests(), currentTokens + tokensTORefill);
        
        if(tokensTORefill > 0 || currentTokens == this.config.getMaxRequests()){
            this.lastRefillTime.put(userId, now);
        }

        Instant instant = Instant.ofEpochMilli(lastRefillTime.get(userId));
        LocalTime time = instant
            .atZone(ZoneId.of("Asia/Kolkata"))   // or your timezone
            .toLocalTime();
        System.out.println("last refill time " + time);

        return currentTokens;
    }

}
