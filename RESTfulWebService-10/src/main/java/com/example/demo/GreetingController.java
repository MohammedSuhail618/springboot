package com.example.demo;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller will ensure that this class will control how the object will be
 * used. It is done using two parts: 
 * 1. Get Mapping 
 * 2. Request Parameters
 */
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private static final String morningTemplate = "Good Morning, %s!";
    private static final String eveningTemplate = "Good Evening, %s!";
    private final AtomicLong counter = new AtomicLong(); // Use a more descriptive name

    // Main greeting endpoint
    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name,
                             @RequestParam(value = "greeting", defaultValue = "hello") String greetingType) {

        String greetingMessage = String.format(template, name);  // Default greeting is "Hello"
        
        // Change greeting based on the 'greeting' parameter
        if ("morning".equalsIgnoreCase(greetingType)) {
            greetingMessage = String.format(morningTemplate, name);
        } else if ("evening".equalsIgnoreCase(greetingType)) {
            greetingMessage = String.format(eveningTemplate, name);
        }
        
        return new Greeting(counter.incrementAndGet(), greetingMessage);
    }

    // Endpoint to get the number of times the greeting has been requested
    @GetMapping("/greetingCount")
    public String getGreetingCount() {
        return "The greeting has been requested " + counter.get() + " times.";
    }
}
