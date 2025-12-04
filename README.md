🚀 OTP Rate Limiter — Spring Boot + Redis

A production-style OTP rate limiting system built using Spring Boot 3, Redis, and Swagger.
This service prevents abuse of OTP APIs by applying phone-number-based and IP-based rate limits.

Perfect for learning API security, Redis caching, and real-world backend design.

🧠 Features
✔ Phone Rate Limiting

Max 3 OTP requests per phone

Time window: 10 minutes

Redis TTL auto-reset

✔ IP Rate Limiting

Max 20 requests per IP per 10 minutes

Prevents bots / abuse

✔ Swagger Documentation

Available at:

http://localhost:8080/docs

✔ Structured JSON Responses

Uses DTO models for clean and predictable API responses.

✔ Global Exception Handling

Graceful error responses instead of raw Spring errors.

✔ Logging

Logs all requests, limits, and errors for debugging.

📁 Project Structure
src/
 ├── main/
 │   ├── java/com/anil/otpratelimiter/
 │   │    ├── controller/RateLimiterController.java
 │   │    ├── service/RateLimiterService.java
 │   │    ├── dto/
 │   │    │     ├── SuccessResponse.java
 │   │    │     └── ErrorResponse.java
 │   │    ├── exception/GlobalExceptionHandler.java
 │   │    └── config/SwaggerConfig.java
 │   └── resources/application.properties

🔌 How It Works
🔸 Phone Flow

User requests OTP

Redis key otp_limit:{phone} increments

If count > 3 → block for 10 minutes

🔸 IP Flow

System checks otp_ip:{ip}

If count > 20 → block access

📝 Example API Usage
➤ Request
POST /otp/send?phone=9876543210

✔ Success Response
{
  "status": "success",
  "message": "OTP sent successfully."
}

❌ Rate Limit Response
{
  "status": "error",
  "reason": "phone_limit_exceeded",
  "retryAfterSeconds": 320
}

▶️ Running the Project
1️⃣ Start Redis
redis-server


Check Redis connection:

redis-cli ping
PONG

2️⃣ Run Spring Boot
mvn spring-boot:run

3️⃣ Test in Swagger
http://localhost:8080/docs

🛠 Tech Stack

Java 17

Spring Boot 3.5

Redis

Spring Data Redis

Lombok

Swagger (SpringDoc OpenAPI)
