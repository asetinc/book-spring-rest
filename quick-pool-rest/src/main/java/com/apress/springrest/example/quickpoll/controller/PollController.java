package com.apress.springrest.example.quickpoll.controller;

import com.apress.springrest.example.quickpoll.domain.Poll;
import com.apress.springrest.example.quickpoll.exception.ResourceNotFoundException;
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
import java.util.logging.Logger;

@RestController
public class PollController {
    private final static Logger LOG = Logger.getLogger(PollController.class.getName());

    @Inject
    private PollRepository pollRepository;

    @RequestMapping(value = "/pollsAsIterable", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPolls() {
        LOG.info("getAllPolls invoked.");
        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @RequestMapping(value = "/pollsAsList", method = RequestMethod.GET)
    public ResponseEntity<?> getAllPollsAsList() {
        LOG.info("getAllPollsAsList invoked.");
        Iterable<Poll> allPolls = pollRepository.findAll();

        List<Poll> allPollsInList = new ArrayList<>();
        allPollsInList.addAll(Lists.newArrayList(allPolls));

        return new ResponseEntity<>(allPollsInList, HttpStatus.OK);
    }

    @RequestMapping(value = "polls", method = RequestMethod.POST)
    public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
        LOG.info("createPoll invoked.");
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
        LOG.info("getPoll invoked.");
        verifyPoll(pollId);
        Poll poll = pollRepository.findOne(pollId);
        return new ResponseEntity<>(poll, HttpStatus.OK);
    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
        LOG.info("updatePoll invoked.");
        verifyPoll(pollId);
        Poll updatedPoll = pollRepository.save(poll);
        return new ResponseEntity<>(updatedPoll, HttpStatus.OK);
    }

    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        LOG.info("deletePoll invoked.");
        verifyPoll(pollId);
        pollRepository.delete(pollId);
        return new ResponseEntity<>("Poll successfully deleted.", HttpStatus.OK);
    }

    private void verifyPoll(Long pollId) throws ResourceNotFoundException {
        if (!pollRepository.exists(pollId)) {
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found.");
        }
    }
}
