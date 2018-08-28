package com.solstice.microstocks.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.equalTo;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.repository.QuoteRepository;
import com.solstice.microstocks.service.LoadUtilService;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:test-dataset.xml")
public class LoadControllerIntegrationTest {

  @Autowired
  private QuoteRepository quoteRepository;

  @Autowired
  private LoadUtilService loadUtilService;

  private LoadController loadController;
  private SimpleDateFormat dateFormat;

  @Before
  public void setup() {
    try {
      ReflectionTestUtils.setField(loadUtilService, "datasetUrl",
          new URL("https://bootcamp-training-files.cfapps.io/week4/week4_stocks.json"));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    loadController = new LoadController(loadUtilService);
    dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  }

  @Test
  public void testPostLoad() {
//    try {
//      ResponseEntity<List<Quote>> quoteResponse = loadController.load();
//
//
//      List<Quote> quotes = quoteResponse.getBody();
//      when(quoteRepository.findAll()).thenReturn(quotes);
//      List<Quote> quotesFromDb = quoteRepository.findAll();
//
//      assertThat(quoteResponse.getStatusCodeValue(), is(equalTo(201)));
//      assertThat(quoteResponse.getBody(), is(notNullValue()));
//      assertFalse(quoteResponse.getBody().isEmpty());
//      assertThat(quotes, is(notNullValue()));
//      assertFalse(quotes.isEmpty());
//      quoteResponse.getBody().forEach(quote -> assertTrue(quotes.contains(quote)));
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
  }

  @Test
  public void testGetLoad() throws ParseException {

//    List<Quote> expectedQuotes = Arrays.asList(
//        new Quote(2, 1111.11, 111, dateFormat.parse("2018-06-22 08:30:00")),
//        new Quote(2, 2222.22, 111, dateFormat.parse("2018-06-22 10:00:00")),
//        new Quote(2, 3333.33, 111, dateFormat.parse("2018-06-22 12:00:00")),
//        new Quote(2, 4444.44, 111, dateFormat.parse("2018-06-22 13:00:00")),
//        new Quote(2, 5555.55, 111, dateFormat.parse("2018-06-22 14:00:00")),
//        new Quote(2, 6666.66, 111, dateFormat.parse("2018-06-22 16:30:00"))
//    );
//    List<Quote> quotes = loadController.getStocks().getBody();
//
//    assertThat(quotes, is(notNullValue()));
//    assertFalse(quotes.isEmpty());
//    quotes.forEach(quote -> assertTrue(expectedQuotes.contains(quote)));
  }
}
