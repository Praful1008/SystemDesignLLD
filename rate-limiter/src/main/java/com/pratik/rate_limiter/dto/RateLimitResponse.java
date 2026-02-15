package com.pratik.rate_limiter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RateLimitResponse {
    private final boolean allowed;
    private final int limit;
    private final int remaining;
    private final long resetSeconds;
}
