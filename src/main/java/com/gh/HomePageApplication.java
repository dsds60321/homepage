package com.gh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HomePageApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomePageApplication.class, args);
    }

}
