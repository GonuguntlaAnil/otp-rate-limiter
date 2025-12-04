package com.anil.otpratelimiter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private final StringRedisTemplate redis;

    private static final int MAX_OTP_LIMIT = 3;            // phone OTP limit
    private static final int MAX_IP_LIMIT = 20;            // IP limit
    private static final long WINDOW_SECONDS = 600;        // 10 minutes

    // ------------------------ PHONE LIMIT LOGIC ------------------------

    public boolean isAllowed(String phone) {
        String key = "otp_limit:" + phone;

        String value = redis.opsForValue().get(key);

        if (value == null) {
            redis.opsForValue().set(key, "1", WINDOW_SECONDS, TimeUnit.SECONDS);
            return true;
        }

        int count = Integer.parseInt(value);

        if (count < MAX_OTP_LIMIT) {
            redis.opsForValue().increment(key);
            return true;
        }

        return false;
    }

    public long getRemainingTime(String phone) {
        return redis.getExpire("otp_limit:" + phone);
    }


    // ------------------------ IP LIMIT LOGIC ------------------------

    public boolean isIpAllowed(String ip) {
        String key = "otp_ip:" + ip;

        String countStr = redis.opsForValue().get(key);

        if (countStr == null) {
            redis.opsForValue().set(key, "1", WINDOW_SECONDS, TimeUnit.SECONDS);
            return true;
        }

        int count = Integer.parseInt(countStr);

        if (count < MAX_IP_LIMIT) {
            redis.opsForValue().increment(key);
            return true;
        }

        return false;
    }

    public long getIpRemainingTime(String ip) {
        return redis.getExpire("otp_ip:" + ip);
    }
}
