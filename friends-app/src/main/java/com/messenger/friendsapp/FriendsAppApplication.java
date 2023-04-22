package com.messenger.friendsapp;

import com.messenger.security.EnableSpringSecurity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.messenger")
@EnableSpringSecurity
public class FriendsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendsAppApplication.class, args);
    }

}
