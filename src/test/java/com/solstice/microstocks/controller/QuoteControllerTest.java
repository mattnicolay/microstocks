package com.solstice.microstocks.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import com.solstice.microstocks.data.AggregateQuote;
import com.solstice.microstocks.data.Quote;
import com.solstice.microstocks.repository.QuoteRepository;
import com.solstice.microstocks.repository.SymbolRepository;
import com.solstice.microstocks.service.QuoteUtilService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class QuoteControllerTest {
  @Mock
  private QuoteRepository quoteRepository;

  @Mock
  private SymbolRepository symbolRepository;

  @InjectMocks
  private QuoteUtilService quoteUtilService;

  private QuoteController quoteController;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    quoteController = new QuoteController(quoteUtilService);
  }

  @Test
  public void testGetAggregateDailyHappyPath() {
    try {
      AggregateQuote expected = new AggregateQuote(
          "GOOG",
          1130.99,
          1120.01,
          1122.57,
          724223,
          new SimpleDateFormat().parse("2018-06-22T16:30:00.000+0000"));

      AggregateQuote quote = quoteController.getAggregateDaily("GOOG", "2018-06-22");

      assertThat(quote, is(equalTo(expected)));

      assertThat(quote.getSymbol(), is(equalTo("GOOG")));
      assertThat(quote.getMaxPrice(), is(equalTo(1130.99)));
      assertThat(quote.getMinPrice(), is(equalTo(1120.01)));
      assertThat(quote.getClosingPrice(), is(equalTo(1122.57)));
      assertThat(quote.getTotalVolume(), is(equalTo(724223)));
      assertThat(quote.getDate().toString(), is(equalTo("2018-06-22T16:30:00.000+0000")));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetAggregateDailyEmptyDataset() {
    try {
      AggregateQuote quote = quoteController.getAggregateDaily("GOOG", "2018-06-22");

      assertThat(quote, is(nullValue()));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetAggregateMonthlyHappyPath() {
    try {
      AggregateQuote expected = new AggregateQuote(
          "GOOG",
          1130.99,
          1120,
          1129.65,
          2159363,
          new SimpleDateFormat().parse("2018-06-26T16:30:00.000+0000"));

      AggregateQuote quote = quoteController.getAggregateMonthly("GOOG", "2018-06");

      assertThat(quote, is(equalTo(expected)));

      assertThat(quote.getSymbol(), is(equalTo("GOOG")));
      assertThat(quote.getMaxPrice(), is(equalTo(1130.99)));
      assertThat(quote.getMinPrice(), is(equalTo(1120)));
      assertThat(quote.getClosingPrice(), is(equalTo(1129.65)));
      assertThat(quote.getTotalVolume(), is(equalTo(2159363)));
      assertThat(quote.getDate().toString(), is(equalTo("2018-06-26T16:30:00.000+0000")));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetAggregateMonthlyEmptyDataset() {
    try {
      AggregateQuote quote = quoteController.getAggregateMonthly("GOOG", "2018-06");

      assertThat(quote, is(nullValue()));
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
