package com.messenger.authandprofile;

import com.messenger.security.EnableSpringSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@EnableSpringSecurity
@ConfigurationPropertiesScan
public class AuthAndProfileApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthAndProfileApplication.class, args);
   }
}
