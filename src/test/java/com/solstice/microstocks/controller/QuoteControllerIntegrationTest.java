package com.solstice.microstocks.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import com.solstice.microstocks.model.AggregateQuote;
import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.repository.QuoteRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuoteControllerIntegrationTest {

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private QuoteController quoteController;

  @Autowired
  private LoadController loadController;

  @Before
  public void setup() {
    if (quoteRepository.findAll().isEmpty()) {
      loadController.load();
    }
  }

  /*
    Requires that symbol service is up and running
  */
  @Test
  public void testGetAggregateDaily() {
    AggregateQuote expected = new AggregateQuote(
        "GOOG",
        1130.98,
        1120,
        1125.07,
        763726,
        LocalDate.of(2018, 6, 22));

    AggregateQuote quote = quoteController.getAggregateDaily("GOOG", "2018-06-22");

    assertThat(quote, is(notNullValue()));

    assertThat(quote.getName(), is(equalTo(expected.getName())));
    assertThat(quote.getMaxPrice(), is(equalTo(expected.getMaxPrice())));
    assertThat(quote.getMinPrice(), is(equalTo(expected.getMinPrice())));
    assertThat(quote.getClosingPrice(), is(equalTo(expected.getClosingPrice())));
    assertThat(quote.getTotalVolume(), is(equalTo(expected.getTotalVolume())));
    assertThat(quote.getDate(), is(equalTo(expected.getDate())));
  }

  /*
  Requires that symbol service is up and running
   */
  @Test
  public void testGetAggregateMonthly() {
    AggregateQuote expected = new AggregateQuote(
        "GOOG",
        1130.99,
        1120,
        1125.52,
        2302903,
        LocalDate.of(2018, 6, 26));

    AggregateQuote quote = quoteController.getAggregateMonthly("GOOG", "2018-06");

    assertThat(quote, is(notNullValue()));

    assertThat(quote.getName(), is(equalTo(expected.getName())));
    assertThat(quote.getMaxPrice(), is(equalTo(expected.getMaxPrice())));
    assertThat(quote.getMinPrice(), is(equalTo(expected.getMinPrice())));
    assertThat(quote.getClosingPrice(), is(equalTo(expected.getClosingPrice())));
    assertThat(quote.getTotalVolume(), is(equalTo(expected.getTotalVolume())));
    assertThat(quote.getDate(), is(equalTo(expected.getDate())));
  }

  @Test
  public void testGetQuotes() {
    ResponseEntity<List<Quote>> quoteResponse = quoteController.getQuotes();
    List<Quote> quotesFromDb = quoteRepository.findAll();

    assertThat(quoteResponse, is(notNullValue()));
    assertThat(quoteResponse.getBody(), is(notNullValue()));

    List<Quote> quotes = quoteResponse.getBody();

    assertFalse(quotes.isEmpty());
    quotes.forEach(quote -> assertTrue(quotesFromDb.contains(quote)));
  }

  @Test
  public void testDeleteQuotes() {
    quoteController.deleteQuotes();

    assertTrue(quoteRepository.findAll().isEmpty());
  }
}
