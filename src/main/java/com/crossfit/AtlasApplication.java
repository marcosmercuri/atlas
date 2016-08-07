package com.crossfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

// Excluded here, but include in SecurityConfiguration
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class})
@SpringBootApplication
public class AtlasApplication {

    public static void main (String[] args) {
        SpringApplication.run(AtlasApplication.class, args);
    }
}
