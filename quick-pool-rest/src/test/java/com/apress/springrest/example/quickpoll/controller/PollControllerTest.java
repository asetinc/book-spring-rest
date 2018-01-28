package com.apress.springrest.example.quickpoll.controller;

import com.apress.springrest.example.quickpoll.QuickpollApplication;
import com.apress.springrest.example.quickpoll.domain.Option;
import com.apress.springrest.example.quickpoll.domain.Poll;
import com.apress.springrest.example.quickpoll.repository.PollRepository;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = QuickpollApplication.class)
@WebAppConfiguration
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PollControllerTest {

    @InjectMocks
    private PollController pollController;

    @Mock
    private PollRepository pollRepository;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateNewPoll() {
        ResponseEntity response = pollController.createPoll(createPoll(1L, "Which is Slovenian national color", Arrays.asList("Blue", "Green", "Red", "Orange")));
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        verify(pollRepository, times(1)).save(any(Poll.class));
    }

    @Test
    public void shouldReturnAllPollInstances() {
        List<Poll> polls = Arrays.asList(
                createPoll(1L, "Which is Slovenian national color", Arrays.asList("Blue", "Green", "Red", "Orange")),
                createPoll(2L, "How is called capital of Slovenia", Arrays.asList("Maribor", "Piran", "Ljubljana", "Celje")));
        when(pollRepository.findAll()).thenReturn(polls);
        ResponseEntity<?> response = pollController.getAllPollsAsList();

        assertThat(response.getStatusCode(), Is.is(HttpStatus.OK));
        assertThat(response.getBody(), is(polls));
        verify(pollRepository, times(1)).findAll();
    }

    @Test
    public void shouldRetrievePollInstance() {
        Poll poll = createPoll(1L, "Which is Slovenian national color", Arrays.asList("Blue", "Green", "Red", "Orange"));
        when(pollRepository.findOne(Matchers.any())).thenReturn(poll);
        ResponseEntity<?> response = pollController.getPoll(poll.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(poll));
        verify(pollRepository, times(1)).findOne(any());
    }

    @Test
    public void shouldDeletePollInstance() {
        Poll poll = createPoll(1L, "Which is Slovenian national color", Arrays.asList("Blue", "Green", "Red", "Orange"));
        ResponseEntity<?> response = pollController.deletePoll(poll.getId());

        assertThat(response.getStatusCode(), Is.is(HttpStatus.OK));
        verify(pollRepository, times(1)).delete(any(Long.class));
    }

    @Test
    public void shouldUpdatePollInstance() {
        Poll poll = createPoll(1L, "Which is Slovenian national color", Arrays.asList("Blue", "Green", "Red", "Orange"));
        ResponseEntity<?> response = pollController.updatePoll(poll, 1L);

        assertThat(response.getStatusCode(), Is.is(HttpStatus.OK));
        verify(pollRepository, times(1)).save(any(Poll.class));
    }

    private Poll createPoll(Long id, String question, List<String> answers) {
        Long optionId = 0L;
        Set<Option> options = new HashSet<Option>();
        for (String answer : answers) {
            optionId++;
            options.add(new Option(optionId, answer));
        }

        return new Poll(id, question, options);
    }

}