package com.pratik.rate_limiter.service;

import java.time.Instant;
import java.util.Deque;

import org.springframework.stereotype.Service;

import com.pratik.rate_limiter.dto.RateLimitResponse;
import com.pratik.rate_limiter.repository.RateLimiterRepository;

@Service
public class RateLimiterService {

    private static final long TIME_WINDOW_MILLIS = 60_000;
    private static final int MAX_REQUESTS = 100;

    private final RateLimiterRepository rateLimiterRepositoryRepository;

    public RateLimiterService(RateLimiterRepository rateLimiterRepositoryRepository) {
        this.rateLimiterRepositoryRepository = rateLimiterRepositoryRepository;
    }

    public RateLimitResponse evaluateRequest(String apiKey) {
        long now = Instant.now().toEpochMilli();
        Deque<Long> timestamps = rateLimiterRepositoryRepository.getUserRequestCounts(apiKey);

        evictExpiredRequests(timestamps, now);

        boolean allowed = timestamps.size() < MAX_REQUESTS;

        if (allowed) {
            rateLimiterRepositoryRepository.addUserRequestCounts(apiKey, now);
        }

        int remaining = Math.max(0, MAX_REQUESTS - timestamps.size());
        long resetSeconds = calculateResetTimeSeconds(timestamps, now);

        return new RateLimitResponse(
                allowed,
                MAX_REQUESTS,
                remaining,
                resetSeconds
        );
    }

    private void evictExpiredRequests(Deque<Long> timestamps, long now) {
        while (!timestamps.isEmpty() && now - timestamps.peekFirst() > TIME_WINDOW_MILLIS) {
            timestamps.pollFirst();
        }
    }

    private long calculateResetTimeSeconds(Deque<Long> timestamps, long now) {
        if (timestamps.isEmpty()) {
            return TIME_WINDOW_MILLIS / 1000;
        }
        long oldest = timestamps.peekFirst();
        return Math.max(0, (TIME_WINDOW_MILLIS - (now - oldest)) / 1000);
    }
}

