package com.apress.springrest.example.quickpoll.controller;

import com.apress.springrest.example.quickpoll.QuickpollApplication;
import com.apress.springrest.example.quickpoll.domain.Option;
import com.apress.springrest.example.quickpoll.domain.Vote;
import com.apress.springrest.example.quickpoll.dto.VoteResult;
import com.apress.springrest.example.quickpoll.repository.PollRepository;
import com.apress.springrest.example.quickpoll.repository.VoteRepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.extractProperty;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuickpollApplication.class)
@WebAppConfiguration
public class ResultsControllerTest {

    @InjectMocks
    private ResultsController resultsController;

    @InjectMocks
    private PollController pollController;

    @InjectMocks
    private VoteController voteController;

    @Mock
    private PollRepository pollRepository;

    @Mock
    private VoteRepository voteRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldReturnPollResults() {
        Vote vote1 = new Vote(1L, new Option(1L, "Black"));
        Vote vote2 = new Vote(2L, new Option(2L, "Red"));
        Vote vote3 = new Vote(3L, new Option(3L, "Green"));
        Vote vote4 = new Vote(4L, new Option(1L, "Black"));
        Vote vote5 = new Vote(5L, new Option(2L, "Red"));
        Vote vote6 = new Vote(6L, new Option(3L, "Green"));
        Vote vote7 = new Vote(7L, new Option(1L, "Black"));
        Vote vote8 = new Vote(8L, new Option(2L, "Red"));
        Vote vote9 = new Vote(9L, new Option(3L, "Green"));
        Vote vote10 = new Vote(10L, new Option(3L, "Green"));
        Vote vote11 = new Vote(11L, new Option(2L, "Red"));
        Vote vote12 = new Vote(12L, new Option(3L, "Green"));
        Vote vote13 = new Vote(13L, new Option(1L, "Black"));
        Vote vote14 = new Vote(14L, new Option(2L, "Red"));
        Vote vote15 = new Vote(15L, new Option(3L, "Green"));
        Vote vote16 = new Vote(16L, new Option(1L, "Black"));
        Vote vote17 = new Vote(17L, new Option(2L, "Red"));
        Vote vote18 = new Vote(18L, new Option(3L, "Green"));
        Vote vote19 = new Vote(19L, new Option(3L, "Green"));
        Vote vote20 = new Vote(20L, new Option(3L, "Green"));

        Iterable<Vote> allVotesFromCurrentPoll = Arrays.asList(
                vote1, vote2, vote3, vote4, vote5, vote6, vote7, vote8, vote9, vote10,
                vote11, vote12, vote13, vote14, vote15, vote16, vote17, vote18, vote19, vote20);

        when(voteRepository.findByPoll(any())).thenReturn(allVotesFromCurrentPoll);


        ResponseEntity<VoteResult> results = resultsController.calculateResults(1L);

        Assert.assertThat(results.getStatusCode(), Is.is(HttpStatus.OK));
        Assert.assertThat(results.getBody().getTotalVotes(), Is.is(20));
        Assertions.assertThat(extractProperty("count").from(results.getBody().getResults())).containsExactlyInAnyOrder(5, 6, 9);
    }
}