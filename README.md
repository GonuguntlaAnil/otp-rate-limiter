# OTP Rate Limiter (Spring Boot + Redis)

A simple OTP rate limiting service built using Spring Boot and Redis.  
It restricts how many OTP requests can be made from the same phone number or IP address within a fixed time window.

## Features
- Limit of 3 OTP requests per phone number (10 minutes)
- Limit of 20 requests per IP address (10 minutes)
- Redis used for counters and TTL
- Structured JSON responses (success and error)
- Global exception handling
- Swagger documentation

## Project Structure
src/main/java/com/anil/otpratelimiter/
├── controller/
├── service/
├── dto/
├── exception/
└── config/


## How It Works
- Redis key `otp_limit:{phone}` tracks phone-based attempts  
- Redis key `otp_ip:{ip}` tracks IP-based attempts  
- Keys expire automatically after 10 minutes  

## API Example
POST /otp/send?phone=9876543210
Success:{ "status": "success", "message": "OTP sent successfully." }

Rate limit exceeded:{ "status": "error", "reason": "phone_limit_exceeded" }


## Tech Stack
- Java 17  
- Spring Boot  
- Redis  
- Spring Data Redis  
- Swagger  
- Lombok  
