package com.metaverse.community_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CommunityAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityAppApplication.class, args);
    }

}
