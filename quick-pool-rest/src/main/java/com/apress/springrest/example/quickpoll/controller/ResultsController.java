package com.apress.springrest.example.quickpoll.controller;

import com.apress.springrest.example.quickpoll.domain.Vote;
import com.apress.springrest.example.quickpoll.dto.OptionsCount;
import com.apress.springrest.example.quickpoll.dto.VoteResult;
import com.apress.springrest.example.quickpoll.repository.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class ResultsController {
    private final static Logger LOG = Logger.getLogger(ResultsController.class.getName());

    @Inject
    private VoteRepository voteRepository;

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public ResponseEntity<VoteResult> calculateResults(@RequestParam Long pollId) {
        LOG.info("calculateResults invoked.");
        Iterable<Vote> allVotes = voteRepository.findByPoll(pollId);

        VoteResult result = new VoteResult();

        int totalVotes = 0;
        Map<Long, OptionsCount> tempMap = new HashMap<Long, OptionsCount>();
        for (Vote v : allVotes) {
            totalVotes++;
            OptionsCount optionsCount = tempMap.get(v.getOption().getId());
            if (optionsCount == null) {
                optionsCount = new OptionsCount();
                optionsCount.setOptionId(v.getOption().getId());
                tempMap.put(v.getOption().getId(), optionsCount);
            }
            optionsCount.setCount(optionsCount.getCount() + 1);
        }

        result.setTotalVotes(totalVotes);
        result.setResults(tempMap.values());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
