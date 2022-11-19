package com.browngoo.hackingspringbootch3reactive;

import java.util.Random;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactorExample {

    public static void main(String[] args) {
        Mono<Integer> source;

        if(new Random().nextBoolean()){
            source = Flux.range(1, 10).elementAt(5);
        } else {
            source = Flux.just(1,2,3,4).elementAt(5);
        }

        source.subscribeOn(Schedulers.parallel())
            .block();
    }
}
