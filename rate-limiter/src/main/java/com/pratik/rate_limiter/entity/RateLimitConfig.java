package com.pratik.rate_limiter.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RateLimitConfig {
    private final int maxRequests;
    private final int windowInSeconds;
}
