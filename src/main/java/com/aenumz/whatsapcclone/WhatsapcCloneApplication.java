package com.aenumz.whatsapcclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WhatsapcCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatsapcCloneApplication.class, args);
    }

}
