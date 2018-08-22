package com.solstice.microstocks.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.solstice.microstocks.data.Quote;
import com.solstice.microstocks.data.Symbol;
import com.solstice.microstocks.repository.QuoteRepository;
import com.solstice.microstocks.service.LoadUtilService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoadControllerIntegrationTest {
  @Mock
  private QuoteRepository quoteRepository;

  @InjectMocks
  private LoadUtilService loadUtilService;

  private LoadController loadController;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    try {
      ReflectionTestUtils.setField(loadUtilService, "datasetUrl",
          new URL("https://bootcamp-training-files.cfapps.io/week2/week2-stocks.json"));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    loadController = new LoadController(loadUtilService);
  }

  @Test
  public void testPostLoad() {
    try {
      ResponseEntity<List<Quote>> quoteResponse = loadController.load();


      List<Quote> quotes = quoteResponse.getBody();
      when(quoteRepository.findAll()).thenReturn(quotes);
      List<Quote> quotesFromDb = quoteRepository.findAll();

      assertThat(quoteResponse.getStatusCodeValue(), is(equalTo(201)));
      assertThat(quoteResponse.getBody(), is(notNullValue()));
      assertFalse(quoteResponse.getBody().isEmpty());
      assertThat(quotes, is(notNullValue()));
      assertFalse(quotes.isEmpty());
      quoteResponse.getBody().forEach(quote -> assertTrue(quotes.contains(quote)));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetLoad() {

    Quote quote = new Quote(new Symbol("TEST"), 1234.56, 789, new Date());
    List<Quote> mockQuotes = Arrays.asList(quote, quote, quote);
    when(quoteRepository.findAll()).thenReturn(mockQuotes);
    List<Quote> quotes = loadController.getStocks();


    assertThat(quotes, is(notNullValue()));
    assertFalse(quotes.isEmpty());
    assertThat(quotes, equalTo(quoteRepository.findAll()));
  }
}
