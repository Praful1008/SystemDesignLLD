package com.pratik.rate_limiter.repository;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class RateLimiterRepository {

    private Map<String, Deque<Long>> userRequestCounts = new HashMap<>();

    public Deque<Long> getUserRequestCounts(String userAPI) {
        return userRequestCounts.computeIfAbsent(userAPI, key -> new ArrayDeque<Long>());
    }

    public void addUserRequestCounts(String userAPI, long timestamp) {
        userRequestCounts.get(userAPI).addLast(timestamp);
    }

    public void removeUserRequestCounts(String userAPI){
        userRequestCounts.get(userAPI).removeFirst();
    }
}
