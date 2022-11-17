package com.browngoo.hackingspringbootch3reactive;

import org.springframework.data.repository.CrudRepository;

public interface BlockingItemRepository extends CrudRepository<Item, String> {

}
