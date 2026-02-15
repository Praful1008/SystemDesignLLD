// package com.pratik.rate_limiter.controller;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.pratik.rate_limiter.dto.RateLimitResponse;
// import com.pratik.rate_limiter.service.RateLimiterService;

// @RestController
// @RequestMapping("/api/v1")
// public class RateLimiterController {

//     private final RateLimiterService rateLimiterService;

//     public RateLimiterController(RateLimiterService rateLimiterService) {
//         this.rateLimiterService = rateLimiterService;
//     }

//     @GetMapping("/health")
//     public ResponseEntity<String> health() {
//         return ResponseEntity.ok("Rate Limiter is Active");
//     }

//     @GetMapping("/quotes")
//     public ResponseEntity<String> getQuotes(@RequestHeader("X-API-KEY") String apiKey) {

//         RateLimitResponse result = rateLimiterService.evaluateRequest(apiKey);

//         HttpHeaders headers = buildRateLimitHeaders(result);

//         if (!result.isAllowed()) {
//             return ResponseEntity
//                     .status(HttpStatus.TOO_MANY_REQUESTS)
//                     .headers(headers)
//                     .body("Rate limit exceeded. Try again later.");
//         }

//         return ResponseEntity
//                 .ok()
//                 .headers(headers)
//                 .body("Rate Limit Status");
//     }

//     private HttpHeaders buildRateLimitHeaders(RateLimitResponse result) {
//         HttpHeaders headers = new HttpHeaders();
//         headers.add("X-RateLimit-Limit", String.valueOf(result.getLimit()));
//         headers.add("X-RateLimit-Remaining", String.valueOf(result.getRemaining()));
//         headers.add("X-RateLimit-Reset", String.valueOf(result.getResetSeconds()));
//         return headers;
//     }
// }