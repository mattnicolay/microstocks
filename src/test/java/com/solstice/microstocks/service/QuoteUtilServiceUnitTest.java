package com.solstice.microstocks.service;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.solstice.microstocks.model.AggregateQuote;
import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.model.TimePeriod;
import com.solstice.microstocks.repository.QuoteRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.client.RestTemplate;
import sun.jvm.hotspot.oops.Instance;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class QuoteUtilServiceUnitTest {

  @Mock
  private QuoteRepository quoteRepository;

  @Mock
  private RestTemplate restTemplate;

  @Mock
  private EurekaClient eurekaClient;

  @Mock
  private InstanceInfo instanceInfo;

  @InjectMocks
  private QuoteUtilService quoteUtilService;

  @Before
  public void setup() throws ParseException {
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
          new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-06-22 16:30:00"));

      when(restTemplate.getForObject(any(String.class), any())).thenReturn(2);
      when(quoteRepository.getAggregateData(any(Integer.class), any(LocalDate.class), any(LocalDate.class))).thenReturn(expected);
      when(eurekaClient.getNextServerFromEureka(any(String.class), any(Boolean.class))).thenReturn(instanceInfo);

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

  //add monthly test

  @Test
  public void testGetAggregateRestTemplateFailure() {
    try {
      AggregateQuote aggregateQuote = quoteUtilService.getAggregate("GOOG", "2018-06-22", TimePeriod.DAY);

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

      AggregateQuote aggregateQuote = quoteUtilService.getAggregate("GOOG", "2018-06-22", TimePeriod.DAY);

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
