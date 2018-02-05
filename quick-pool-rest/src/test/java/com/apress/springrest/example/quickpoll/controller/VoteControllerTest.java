package com.apress.springrest.example.quickpoll.controller;

import com.apress.springrest.example.quickpoll.QuickpollApplication;
import com.apress.springrest.example.quickpoll.domain.Option;
import com.apress.springrest.example.quickpoll.domain.Vote;
import com.apress.springrest.example.quickpoll.repository.VoteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuickpollApplication.class)
@WebAppConfiguration
public class VoteControllerTest {

    @InjectMocks
    VoteController voteController;

    @Mock
    VoteRepository voteRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateVote() {
        Vote vote = new Vote(1L, new Option(1L, "Green"));
        voteController.createVote(1l, vote);
        verify(voteRepository, times(1)).save(any(Vote.class));
    }

    @Test
    public void shouldReturnAllVotes() {
        Vote vote1 = new Vote(1L, new Option(1L, "Black"));
        Vote vote2 = new Vote(2L, new Option(2L, "Red"));
        Vote vote3 = new Vote(3L, new Option(3L, "Green"));

        Iterable<Vote> votes = Arrays.asList(vote1, vote2, vote3);
        when(voteRepository.findByPoll(any())).thenReturn(votes);

        Iterable<Vote> result = voteController.getAllVotes(1L);

        assertThat(result).isNotEmpty().hasSize(3);
        assertThat(result).contains(vote1, vote2, vote3);
        assertThat(result).doesNotContainNull().doesNotHaveDuplicates();
        assertThat(extractProperty("option").ofType(Option.class).from(result));
        assertThat(extractProperty("id").from(result)).contains(1L, 2L, 3L).doesNotContain(4L);
        assertThat(extractProperty("option.value").from(result)).contains("Black", "Red", "Green");

        verify(voteRepository, times(1)).findByPoll(any());
    }
}