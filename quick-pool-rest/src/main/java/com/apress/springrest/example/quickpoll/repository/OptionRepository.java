package com.apress.springrest.example.quickpoll.repository;

import com.apress.springrest.example.quickpoll.domain.Option;
import org.springframework.data.repository.CrudRepository;

public interface OptionRepository extends CrudRepository<Option, Long> {
}
