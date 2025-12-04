package com.anil.otpratelimiter.controller;

import com.anil.otpratelimiter.dto.ErrorResponse;
import com.anil.otpratelimiter.dto.SuccessResponse;
import com.anil.otpratelimiter.service.RateLimiterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/otp")
public class RateLimiterController {

    private final RateLimiterService service;

    @PostMapping("/send")
    @Operation(summary = "Send OTP", description = "Sends OTP with rate limiting applied (phone + IP)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OTP sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid phone number"),
            @ApiResponse(responseCode = "429", description = "Rate limit exceeded")
    })
    public ResponseEntity<?> sendOtp(@RequestParam String phone, HttpServletRequest request) {

        String clientIp = request.getRemoteAddr();

        // IP Rate Limit Check
        boolean ipAllowed = service.isIpAllowed(clientIp);
        if (!ipAllowed) {
            long wait = service.getIpRemainingTime(clientIp);
            return ResponseEntity.status(429).body(
                    new ErrorResponse("error", "ip_limit_exceeded", wait)
            );
        }

        // Phone Number Validation
        if (!phone.matches("\\d{10}")) {
            return ResponseEntity.badRequest().body(
                    new ErrorResponse("error", "invalid_phone", 0)
            );
        }

        // Phone Rate Limit Check
        boolean allowed = service.isAllowed(phone);
        if (!allowed) {
            long wait = service.getRemainingTime(phone);
            return ResponseEntity.status(429).body(
                    new ErrorResponse("error", "phone_limit_exceeded", wait)
            );
        }

        // Allowed
        return ResponseEntity.ok(
                new SuccessResponse("success", "OTP sent successfully.")
        );
    }
}
