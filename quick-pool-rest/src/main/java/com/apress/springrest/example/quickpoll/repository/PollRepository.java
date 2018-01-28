package com.apress.springrest.example.quickpoll.repository;

import com.apress.springrest.example.quickpoll.domain.Poll;
import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {
}
