package com.anil.otpratelimiter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Rate limit or validation error response")
public class ErrorResponse {

    @Schema(description = "Status of API", example = "error")
    private String status;

    @Schema(description = "Reason for failure", example = "phone_limit_exceeded")
    private String reason;

    @Schema(description = "Retry time in seconds", example = "320")
    private long retryAfterSeconds;
}
