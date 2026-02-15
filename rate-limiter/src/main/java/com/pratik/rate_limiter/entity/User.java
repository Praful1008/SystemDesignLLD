package com.pratik.rate_limiter.entity;

import com.pratik.rate_limiter.enums.UserTier;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class User {
    private final String userId;
    private final UserTier tier;
}
