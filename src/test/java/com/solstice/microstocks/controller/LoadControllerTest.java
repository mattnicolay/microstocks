package com.solstice.microstocks.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;

import com.solstice.microstocks.data.Quote;
import com.solstice.microstocks.repository.QuoteRepository;
import com.solstice.microstocks.service.LoadUtilService;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoadControllerTest {
  @Mock
  private QuoteRepository quoteRepository;

  @InjectMocks
  private LoadUtilService loadUtilService;

  private LoadController loadController;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    loadController = new LoadController(loadUtilService);
  }

  @Test
  public void testPostLoadHappyPath() {
    try {
      ResponseEntity<List<Quote>> quoteResponse = loadController.load();

      assertThat(quoteResponse.getStatusCodeValue(), is(equalTo(201)));
      assertThat(quoteRepository.findAll(), contains(any(Quote.class)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetLoadHappyPath() {
    List<Quote> quotes = loadController.getStocks();

    assertThat(quotes, contains(any(Quote.class)));
    assertThat(quotes, equalTo(quoteRepository.findAll()));
  }
}
