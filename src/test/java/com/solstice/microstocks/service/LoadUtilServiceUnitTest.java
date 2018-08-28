package com.solstice.microstocks.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.repository.QuoteRepository;
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
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
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
          new URL("https://bootcamp-training-files.cfapps.io/week4/week4_stocks.json"));
      ReflectionTestUtils.setField(loadUtilService, "profile", "local");
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
  public void testLoadQuotesCloudProfile() {
    ReflectionTestUtils.setField(loadUtilService, "profile", "cloud");
    try {
      List<Quote> quotes = loadUtilService.loadQuotes();

      assertFalse(quotes.isEmpty());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }



  @Test
  public void testLoadQuotesJsonFailure() {
    try {
      ReflectionTestUtils.setField(loadUtilService, "datasetUrl",
          new URL("https://bootcamp-training-files.cfapps.io/week4/week4-stocks"));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    try {
      List<Quote> quotes = loadUtilService.loadQuotes();

      fail();
    } catch (IOException e) {
    }
  }


}
