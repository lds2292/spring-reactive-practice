package com.browngoo.hackingspringbootch6reactive;

import static org.springframework.data.mongodb.core.query.Criteria.byExample;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.mongodb.core.ReactiveFluentMongoOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class InventoryService {

    private final ItemRepository itemRepository;
    private final ReactiveFluentMongoOperations fluentOperations;

    public InventoryService(ItemRepository itemRepository,
        ReactiveFluentMongoOperations fluentOperations) {
        this.itemRepository = itemRepository;
        this.fluentOperations = fluentOperations;
    }

    Flux<Item> searchByExample(String name, String description, boolean useAnd){
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (useAnd
            ? ExampleMatcher.matchingAll() : ExampleMatcher.matchingAny()
        ).withStringMatcher(StringMatcher.CONTAINING)
            .withIgnoreCase()
            .withIgnorePaths("price");

        Example<Item> probe = Example.of(item, matcher);
        return itemRepository.findAll(probe);
    }

    Flux<Item> searchByFluentExample(String name, String description) {
        return fluentOperations.query(Item.class)
            .matching(query(where("TV tray").is(name).and("Smurf").is(description)))
            .all();
    }

    Flux<Item> searchByFluentExample(String name, String description, boolean useAnd){
        Item item = new Item(name, description, 0.0);

        ExampleMatcher matcher = (useAnd ? ExampleMatcher.matchingAll()
            : ExampleMatcher.matchingAny())
            .withStringMatcher(StringMatcher.CONTAINING)
            .withIgnoreCase()
            .withIgnorePaths("price");

        return fluentOperations.query(Item.class)
            .matching(query(byExample(Example.of(item, matcher))))
            .all();

    }

}
