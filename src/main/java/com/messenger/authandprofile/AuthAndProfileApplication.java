package com.messenger.authandprofile;

import com.messenger.authandprofile.domain.service.DomainUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthAndProfileApplication {

    public static void main(String[] args) {

        var context = SpringApplication.run(AuthAndProfileApplication.class, args);

        var domainUserService = (DomainUserService) context.getBean("DomainUserServiceImpl");
        System.out.println(domainUserService.getClass());
    }

}
