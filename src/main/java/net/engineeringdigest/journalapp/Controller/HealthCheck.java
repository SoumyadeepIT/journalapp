package net.engineeringdigest.journalapp.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
        // This is a placeholder for health check logic
        // You can implement actual health check logic here
    }
}
