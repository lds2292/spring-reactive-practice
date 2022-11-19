package com.browngoo.hackingspringbootch3reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.thymeleaf.TemplateEngine;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class HackingSpringBootCh3ReactiveApplication {

    public static void main(String[] args) {

        BlockHound.builder()
                .allowBlockingCallsInside(
                    TemplateEngine.class.getCanonicalName(), "process"
                ).install();

        SpringApplication.run(HackingSpringBootCh3ReactiveApplication.class, args);
    }

}
