package com.example.ratelimiter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimiter {
    private final int maxRequests;
    private final long windowSizeInMillis;
    private final Map<String, Bucket> clientBuckets = new ConcurrentHashMap<>();

    public RateLimiter(@Value("${ratelimiter.maxRequests}") int maxRequests, 
                       @Value("${ratelimiter.windowSizeInMillis}") long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    public boolean isAllowed(String clientId) {
        long currentTime = System.currentTimeMillis();
        clientBuckets.putIfAbsent(clientId, new Bucket(maxRequests, currentTime, windowSizeInMillis));

        Bucket bucket = clientBuckets.get(clientId);
        return bucket.allowRequest(currentTime);
    }

    private static class Bucket {
        private int tokens;
        private long lastRefillTimestamp;
        private final int maxTokens;
        private final long refillInterval;

        public Bucket(int maxTokens, long currentTime, long refillInterval) {
            this.tokens = maxTokens;
            this.maxTokens = maxTokens;
            this.lastRefillTimestamp = currentTime;
            this.refillInterval = refillInterval;
        }

        public synchronized boolean allowRequest(long currentTime) {
            refillTokens(currentTime);
            if (tokens > 0) {
                tokens--;
                return true;
            }
            return false;
        }

        private void refillTokens(long currentTime) {
            long elapsedTime = currentTime - lastRefillTimestamp;
            long tokensToAdd = elapsedTime / refillInterval;

            if (tokensToAdd > 0) {
                tokens = Math.min(maxTokens, tokens + (int) tokensToAdd);
                lastRefillTimestamp = currentTime;
            }
        }
    }
}
