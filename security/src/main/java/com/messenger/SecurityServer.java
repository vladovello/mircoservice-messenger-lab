package com.messenger;

import com.messenger.security.EnableSpringSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@EnableSpringSecurity
@ConfigurationPropertiesScan("com.messenger")
@SpringBootApplication
public class SecurityServer {
    public static void main(String[] args) {
        SpringApplication.run(SecurityServer.class, args);
    }
}
