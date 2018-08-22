package com.solstice.microstocks.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.solstice.microstocks.data.Quote;
import com.solstice.microstocks.data.Symbol;
import com.solstice.microstocks.repository.QuoteRepository;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
//@TestPropertySource("classpath:application.properties")
public class LoadUtilServiceUnitTest {

  @Mock
  private QuoteRepository quoteRepository;

  @InjectMocks
  private LoadUtilService loadUtilService;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    try {
      ReflectionTestUtils.setField(loadUtilService, "datasetUrl",
          new URL("https://bootcamp-training-files.cfapps.io/week2/week2-stocks.json"));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testLoadQuotes() {
    try {
      List<Quote> quotes = loadUtilService.loadQuotes();

      assertFalse(quotes.isEmpty());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testFindAll() {
    Quote quote = new Quote(new Symbol("TEST"), 1234.56, 789, new Date());
    List<Quote> quotes = Arrays.asList(quote, quote, quote);
    when(quoteRepository.findAll()).thenReturn(quotes);
    List<Quote> quotesFromService = loadUtilService.findAll();

    assertFalse(quotesFromService.isEmpty());
    quotesFromService.forEach(q -> assertTrue(quotes.contains(q)));
  }

  @Test
  public void testLoadQuotesJsonFailure() {
    try {
      ReflectionTestUtils.setField(loadUtilService, "datasetUrl",
          new URL("https://bootcamp-training-files.cfapps.io/week2/week2-stocks"));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    try {
      List<Quote> quotes = loadUtilService.loadQuotes();

      fail();
    } catch (IOException e) {
    }
  }

  @Test
  public void testFindAllEmptyResult() {
    List<Quote> quotes = loadUtilService.findAll();

    assertTrue(quotes.isEmpty());
  }

}
