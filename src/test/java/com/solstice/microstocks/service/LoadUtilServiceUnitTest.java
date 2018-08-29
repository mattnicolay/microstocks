package com.solstice.microstocks.service;

import static org.junit.Assert.*;

import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.repository.QuoteRepository;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class LoadUtilServiceUnitTest {

  private Logger logger = LoggerFactory.getLogger(LoadUtilServiceUnitTest.class);

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
      logger.error("MalformedURLException thrown in setup method: {}", e.toString());
    }
  }

  @Test
  public void testLoadQuotes() {
    try {
      List<Quote> quotes = loadUtilService.loadQuotes();

      assertFalse(quotes.isEmpty());
    } catch (IOException e) {
      logger.error("IOException thrown in testLoadQuotes method: {}", e.toString());
    }
  }

  @Test
  public void testLoadQuotesCloudProfile() {
    ReflectionTestUtils.setField(loadUtilService, "profile", "cloud");
    try {
      List<Quote> quotes = loadUtilService.loadQuotes();

      assertFalse(quotes.isEmpty());
    } catch (IOException e) {
      logger.error("IOException thrown in testLoadQuotesCloudProfile method: {}", e.toString());
    }
  }



  @Test
  public void testLoadQuotesJsonFailure() {
    try {
      ReflectionTestUtils.setField(loadUtilService, "datasetUrl",
          new URL("https://bootcamp-training-files.cfapps.io/week4/week4-stocks"));
    } catch (MalformedURLException e) {
      logger.error("MalformedURLException thrown in testLoadQuotesJsonFailure method: {}", e.toString());
    }
    try {
      loadUtilService.loadQuotes();

      fail();
    } catch (IOException e) {
    }
  }


}
