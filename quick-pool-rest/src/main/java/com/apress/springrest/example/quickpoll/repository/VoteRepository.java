package com.apress.springrest.example.quickpoll.repository;

import com.apress.springrest.example.quickpoll.domain.Vote;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {
}
