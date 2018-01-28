package com.apress.springrest.example.quickpoll.controller;

import com.apress.springrest.example.quickpoll.domain.Poll;
import com.apress.springrest.example.quickpoll.repository.PollRepository;
import org.assertj.core.util.Lists;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PollController {

    @Inject
    private PollRepository pollRepository;

    @RequestMapping(value = "/pollsAsIterable", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPolls() {
        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @RequestMapping(value = "/pollsAsList", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPollsAsList() {
        Iterable<Poll> allPolls = pollRepository.findAll();

        List<Poll> allPollsInList = new ArrayList<>();
        allPollsInList.addAll(Lists.newArrayList(allPolls));

        return new ResponseEntity<>(allPollsInList, HttpStatus.OK);
    }

    @RequestMapping(value = "polls", method = RequestMethod.POST)
    public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
        pollRepository.save(poll);

        URI pollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(pollUri);

        return new ResponseEntity<>(poll, headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        Poll poll = pollRepository.findOne(pollId);
        return new ResponseEntity<>(poll, HttpStatus.OK);

    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
        Poll updatedPoll = pollRepository.save(poll);
        return new ResponseEntity<>(updatedPoll, HttpStatus.OK);
    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        pollRepository.delete(pollId);
        return new ResponseEntity<>("Poll successfully deleted.", HttpStatus.OK);
    }
}
