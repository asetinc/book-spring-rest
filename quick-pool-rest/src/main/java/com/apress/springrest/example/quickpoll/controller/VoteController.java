package com.apress.springrest.example.quickpoll.controller;

import com.apress.springrest.example.quickpoll.domain.Vote;
import com.apress.springrest.example.quickpoll.repository.VoteRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.util.logging.Logger;

@RestController
public class VoteController {
    private final static Logger LOG = Logger.getLogger(VoteController.class.getName());

    @Inject
    private VoteRepository voteRepository;

    @RequestMapping(value = "/polls/{pollId}/votes")
    public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote vote) {
        LOG.info("createVote invoked.");
        voteRepository.save(vote);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vote.getId())
                .toUri());

        return new ResponseEntity<>(vote, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/polls/{pollId}/votes", method = RequestMethod.GET)
    public Iterable<Vote> getAllVotes(@PathVariable Long pollId) {
        LOG.info("getAllVotes invoked.");
        return voteRepository.findByPoll(pollId);
    }
}
