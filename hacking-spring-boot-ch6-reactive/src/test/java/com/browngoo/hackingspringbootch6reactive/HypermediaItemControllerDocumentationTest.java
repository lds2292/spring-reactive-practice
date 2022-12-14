package com.browngoo.hackingspringbootch6reactive;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = HypermediaItemController.class)
@AutoConfigureRestDocs
public class HypermediaItemControllerDocumentationTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    InventoryService service;

    @MockBean
    ItemRepository repository;

    @Test
    void findOneItem(){
        when(repository.findById("item-1")).thenReturn(
            Mono.just(new Item("item-1", "Alf alarm clock", "nothing I really need", 19.99))
        );

        this.webTestClient.get().uri("/hypermedia/items/item-1")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .consumeWith(
                document("findOne-hypermedia", preprocessResponse(prettyPrint()),
                    links(
                        linkWithRel("self").description("이 `Item`에 대한 공식링크"),
                        linkWithRel("item").description("`Item` 목록 링크")
                    )
                )
            );

    }
}
