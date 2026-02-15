package com.pratik.rate_limiter;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pratik.rate_limiter.entity.User;
import com.pratik.rate_limiter.enums.UserTier;
import com.pratik.rate_limiter.service.RateLimiterService;


public class RateLimiterApplication {

	public static void main(String[] args) throws InterruptedException {
		RateLimiterService rateLimiterService = new RateLimiterService();
		
		User freeUser = new User("user1", UserTier.FREE);
		User premiumUser = new User("user2", UserTier.PREMIUM);

       System.out.println("=== Free User Requests ===");
	   
       for (int i = 1; i <= 25; i++) {
           boolean allowed = rateLimiterService.allowRequest(freeUser);
           System.out.println("Request " + i + " for Free User: " + (allowed ? "ALLOWED" : "BLOCKED"));
           System.out.println();
           Thread.sleep(1000);
       }

    //    System.out.println("\n=== Premium User Requests ===");
    //    for (int i = 1; i <= 120; i++) {
    //        boolean allowed = rateLimiterService.allowRequest(premiumUser);
    //        System.out.println("Request " + i + " for Premium User: " + (allowed ? "ALLOWED" : "BLOCKED"));
    //        Thread.sleep(100);
    //    }
	}
}
