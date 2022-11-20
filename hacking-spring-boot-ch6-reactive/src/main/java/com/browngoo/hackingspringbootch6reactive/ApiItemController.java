package com.browngoo.hackingspringbootch6reactive;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ApiItemController {

    private final ItemRepository itemRepository;

    public ApiItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/api/items")
    Flux<Item> findAll() {
        return this.itemRepository.findAll();

    }

    @GetMapping("/api/items/{id}")
    Mono<Item> findOne(
        @PathVariable String id
    ) {
        return this.itemRepository.findById(id);
    }

    @PostMapping("/api/items")
    Mono<ResponseEntity<?>> addNewItem(@RequestBody Mono<Item> item) {
        return item.flatMap(this.itemRepository::save)
            .map(savedItem -> ResponseEntity.created(URI.create("/api/items/" + savedItem.getId()))
                .body(savedItem)
            );
    }

    @PutMapping("/api/items/{id}")
    Mono<ResponseEntity<?>> updateItem(
        @RequestBody Mono<Item> item,
        @PathVariable String id
    ){
        return item.map(content -> new Item(id, content.getName(), content.getDescription(), content.getPrice()))
            .flatMap(this.itemRepository::save)
            .map(ResponseEntity::ok);
    }
}
