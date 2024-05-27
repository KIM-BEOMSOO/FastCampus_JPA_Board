package com.fastcampus.ch4;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Objects;

public interface BoardRepository extends CrudRepository<Board, Long> {

}
