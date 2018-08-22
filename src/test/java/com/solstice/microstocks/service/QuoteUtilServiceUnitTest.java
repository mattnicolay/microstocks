package com.solstice.microstocks.service;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.solstice.microstocks.data.AggregateQuote;
import com.solstice.microstocks.data.TimePeriod;
import com.solstice.microstocks.repository.QuoteRepository;
import com.solstice.microstocks.repository.SymbolRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

  @Mock
  private SymbolRepository symbolRepository;

  @InjectMocks
  private QuoteUtilService quoteUtilService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetAggregateDaily() {
    try {
      AggregateQuote expected = new AggregateQuote(
          "GOOG",
          1130.99,
          1120.01,
          1122.57,
          724223,
          new SimpleDateFormat().parse("2018-06-22T16:30:00.000+0000"));

      AggregateQuote aggregateQuote = quoteUtilService.getAggregate("GOOG", "2018-06-22", TimePeriod.DAY, "yyyy-MM-dd");

      assertThat(aggregateQuote, is(equalTo(expected)));
    } catch (ParseException e) {
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
          new SimpleDateFormat().parse("2018-06-26T16:30:00.000+0000"));

      AggregateQuote aggregateQuote = quoteUtilService.getAggregate("GOOG", "2018-06", TimePeriod.MONTH, "yyyy-MM");

      assertThat(aggregateQuote, is(equalTo(expected)));
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }
}
