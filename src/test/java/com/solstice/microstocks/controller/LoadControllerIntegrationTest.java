package com.solstice.microstocks.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;

import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.repository.QuoteRepository;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoadControllerIntegrationTest {

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private LoadController loadController;

  @Test
  public void testPostLoad() {
    ResponseEntity<List<Quote>> quoteResponse = loadController.load();


    List<Quote> quotes = quoteResponse.getBody();
    List<Quote> quotesFromDb = quoteRepository.findAll();

    assertThat(quoteResponse.getStatusCodeValue(), is(equalTo(201)));
    assertThat(quotes, is(notNullValue()));
    assertFalse(quotes.isEmpty());
    quotes.forEach(quote -> assertTrue(quotesFromDb.contains(quote)));
  }
}
