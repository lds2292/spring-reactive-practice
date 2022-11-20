package com.browngoo.hackingspringbootch6reactive;

import org.springframework.data.repository.CrudRepository;

public interface BlockingItemRepository extends CrudRepository<Item, String> {

}
