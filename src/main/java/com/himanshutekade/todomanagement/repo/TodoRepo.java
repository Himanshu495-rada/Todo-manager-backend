package com.himanshutekade.todomanagement.repo;

import com.himanshutekade.todomanagement.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepo extends JpaRepository<Todo, Long> {

}
