package com.solstice.microstocks.service;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.solstice.microstocks.model.AggregateQuote;
import com.solstice.microstocks.model.TimePeriod;
import com.solstice.microstocks.repository.QuoteRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class QuoteUtilServiceUnitTest {

  @Mock
  private QuoteRepository quoteRepository;

  @Mock
  private RestTemplate restTemplate;

  @InjectMocks
  private QuoteUtilService quoteUtilService;

  private DateFormat dateFormat;
  private AggregateQuote expected;

  @Before
  public void setup() throws ParseException {
    MockitoAnnotations.initMocks(this);
    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    expected = new AggregateQuote(
        "GOOG",
        1130.99,
        1120,
        1129.65,
        2159363,
        dateFormat.parse("2018-06-22 16:30:00"));
  }

  @Test
  public void testGetAggregateDaily() {
    try {
      when(restTemplate.getForObject(any(String.class), any())).thenReturn(2);
      when(quoteRepository.getAggregateData(any(Integer.class), any(Date.class), any(Date.class))).thenReturn(expected);

      AggregateQuote aggregateQuote = quoteUtilService.getAggregate("GOOG", "2018-06-22", TimePeriod.DAY, "yyyy-MM-dd");

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
      expected.setDate(dateFormat.parse("2018-06-26 16:30:00"));

      when(restTemplate.getForObject(any(String.class), any())).thenReturn(2);
      when(quoteRepository.getAggregateData(any(Integer.class), any(Date.class), any(Date.class))).thenReturn(expected);

      AggregateQuote aggregateQuote = quoteUtilService.getAggregate("GOOG", "2018-06", TimePeriod.MONTH, "yyyy-MM");

      assertThat(aggregateQuote.getName(), is(equalTo(expected.getName())));
      assertThat(aggregateQuote.getMaxPrice(), is(equalTo(expected.getMaxPrice())));
      assertThat(aggregateQuote.getMinPrice(), is(equalTo(expected.getMinPrice())));
      assertThat(aggregateQuote.getClosingPrice(), is(equalTo(expected.getClosingPrice())));
      assertThat(aggregateQuote.getTotalVolume(), is(equalTo(expected.getTotalVolume())));
      assertThat(aggregateQuote.getDate(), is(equalTo(expected.getDate())));
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }
}
