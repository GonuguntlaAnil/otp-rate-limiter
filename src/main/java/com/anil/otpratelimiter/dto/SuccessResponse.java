package com.anil.otpratelimiter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Successful OTP send response")
public class SuccessResponse {

    @Schema(description = "Status of API call", example = "success")
    private String status;

    @Schema(description = "Message returned by API", example = "OTP sent successfully.")
    private String message;
}
