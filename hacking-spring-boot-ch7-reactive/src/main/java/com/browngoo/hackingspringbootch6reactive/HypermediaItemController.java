package com.browngoo.hackingspringbootch6reactive;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HypermediaItemController {

    private final ItemRepository repository;

    public HypermediaItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/hypermedia/items/{id}")
    Mono<EntityModel<Item>> findOne(
        @PathVariable String id
    ) {
        HypermediaItemController controller = methodOn(HypermediaItemController.class);

        Mono<Link> selfLink = linkTo(controller.findOne(id)).withSelfRel().toMono();
        Mono<Link> aggregateLink = linkTo(controller.findAll()).withRel(IanaLinkRelations.ITEM).toMono();

        return Mono.zip(repository.findById(id), selfLink, aggregateLink)
            .map(o -> EntityModel.of(o.getT1(), Links.of(o.getT2(), o.getT3())));

    }

    @GetMapping("/hypermedia/items")
    Flux<Item> findAll(){
        return this.repository.findAll();
    }
}
