package com.browngoo.hackingspringbootch2reactive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class TemplateDatabaseLoader {
    @Bean
    CommandLineRunner initialize(MongoOperations mongo){
        return args -> {
            mongo.save(new Item("Alf alarm clock", "clock", 19.99));
            mongo.save(new Item("Smurf TV tray", "Tv", 24.99));
        };
    }
}
