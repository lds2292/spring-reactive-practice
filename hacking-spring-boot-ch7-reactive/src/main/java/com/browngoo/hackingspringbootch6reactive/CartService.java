package com.browngoo.hackingspringbootch6reactive;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CartService {

    private ItemRepository itemRepository;
    private CartRepository cartRepository;

    public CartService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    Mono<Cart> addToCart(String cartId, String id){
        return this.cartRepository.findById(cartId)
            .log("foundCart")
            .defaultIfEmpty(new Cart(cartId))
            .log("emptyCart")
            .flatMap(
                cart -> cart.getCartItems().stream()
                    .filter(cartItem -> cartItem.getItem().getId().equals(id))
                    .findAny()
                    .map(cartItem -> {
                        cartItem.increment();
                        return Mono.just(cart);
                    })
                    .orElseGet(()->{
                        return this.itemRepository.findById(id)
                            .map(CartItem::new)
                            .map(cartItem -> {
                                cart.getCartItems().add(cartItem);
                                return cart;
                            });
                    }))
            .log("cartWithAnotherItem")
            .flatMap(this.cartRepository::save)
            .log("savedCart");
    }
}
