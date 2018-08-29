package com.solstice.microstocks.service;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.solstice.microstocks.model.AggregateQuote;
import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.model.TimePeriod;
import com.solstice.microstocks.repository.QuoteRepository;
import java.time.LocalDate;
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

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class QuoteUtilServiceUnitTest {

  @Mock
  private QuoteRepository quoteRepository;

  @InjectMocks
  private QuoteUtilService quoteUtilService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetAggregate() {
    try {
      AggregateQuote expected = new AggregateQuote(
          "GOOG",
          1130.99,
          1120,
          1129.65,
          2159363,
          LocalDate.parse("2018-06-22"));

      AggregateQuote aggregateQuote = quoteUtilService.getAggregate("GOOG", "2018-06-22", TimePeriod.DAY);

      assertThat(aggregateQuote.getName(), is(equalTo(expected.getName())));
      assertThat(aggregateQuote.getMaxPrice(), is(equalTo(expected.getMaxPrice())));
      assertThat(aggregateQuote.getMinPrice(), is(equalTo(expected.getMinPrice())));
      assertThat(aggregateQuote.getClosingPrice(), is(equalTo(expected.getClosingPrice())));
      assertThat(aggregateQuote.getTotalVolume(), is(equalTo(expected.getTotalVolume())));
      assertThat(aggregateQuote.getDate(), is(equalTo(expected.getDate())));
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  @Test
  public void testGetAggregateMonthly() {
    try {
      AggregateQuote expected = new AggregateQuote(
          "GOOG",
          1130.99,
          1120,
          1129.65,
          2159363,
          LocalDate.parse("2018-06-26"));

      AggregateQuote aggregateQuote = quoteUtilService.getAggregate("GOOG", "2018-06-01", TimePeriod.MONTH);

      assertThat(aggregateQuote.getName(), is(equalTo(expected.getName())));
      assertThat(aggregateQuote.getMaxPrice(), is(equalTo(expected.getMaxPrice())));
      assertThat(aggregateQuote.getMinPrice(), is(equalTo(expected.getMinPrice())));
      assertThat(aggregateQuote.getClosingPrice(), is(equalTo(expected.getClosingPrice())));
      assertThat(aggregateQuote.getTotalVolume(), is(equalTo(expected.getTotalVolume())));
      assertThat(aggregateQuote.getDate(), is(equalTo(expected.getDate())));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetAggregateRestTemplateFailure() {
    try {
      quoteUtilService.getAggregate("GOOG", "2018-06-22", TimePeriod.DAY);

      fail();
    } catch (Exception e) {

    }
  }

  @Test
  public void testGetAggregateNotFoundInRepo() {
    try {
      when(quoteRepository
          .getAggregateData(any(Integer.class), any(LocalDate.class), any(LocalDate.class)))
          .thenThrow(new Exception("Not found"));

      quoteUtilService.getAggregate("GOOG", "2018-06-22", TimePeriod.DAY);

      fail();
    } catch (Exception e) {

    }
  }

  @Test
  public void testFindAll() {
    Quote quote = new Quote(1, 1234.56, 789, new Date());
    List<Quote> quotes = Arrays.asList(quote, quote, quote);
    when(quoteRepository.findAll()).thenReturn(quotes);
    List<Quote> quotesFromService = quoteUtilService.findAll();

    assertFalse(quotesFromService.isEmpty());
  }


  @Test
  public void testFindAllEmptyResult() {
    List<Quote> quotes = quoteUtilService.findAll();

    assertTrue(quotes.isEmpty());
  }
}
