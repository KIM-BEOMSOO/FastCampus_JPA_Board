package com.fastcampus.ch4;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartRepository extends CrudRepository<Cart, Long> {

}
