package com.browngoo.hackingspringboot.reactive;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class KitchenService {
    Flux<Dish> getDishes(){
        return Flux.<Dish> generate(sink -> sink.next(randomDish()))
            .delayElements(Duration.ofMillis(250));

    }

    private Random picker = new Random();

    private List<Dish> menu = Arrays.asList(
        new Dish("Sesame chicken"),
        new Dish("Lo mein noodles, plain"),
        new Dish("Sweet & sour beef")
    );

    private Dish randomDish() {
        return menu.get(picker.nextInt(menu.size()));
    }
}
