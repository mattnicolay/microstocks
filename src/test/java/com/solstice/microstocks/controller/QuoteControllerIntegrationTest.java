package com.solstice.microstocks.controller;

//import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.solstice.microstocks.data.AggregateQuote;
import com.solstice.microstocks.data.Quote;
import com.solstice.microstocks.data.Symbol;
import com.solstice.microstocks.repository.QuoteRepository;
import com.solstice.microstocks.repository.SymbolRepository;
import com.solstice.microstocks.service.QuoteUtilService;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(QuoteController.class)
//@DatabaseSetup("classpath:")
public class QuoteControllerIntegrationTest {

  @Mock
  private QuoteRepository quoteRepository;

  @Mock
  private SymbolRepository symbolRepository;

  @Mock
  private QuoteUtilService quoteUtilService;

  private MockMvc mockMvc;

  @InjectMocks
  private QuoteController quoteController;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(quoteController).build();
  }

  @Test
  public void testGetAggregateDaily() throws Exception {
//    try {
//      Date testDateStart = new Date(1530019800);
//      Date testDateEnd = new Date(1529703000);
//      AggregateQuote expected = new AggregateQuote(
//          "GOOG",
//          4000,
//          1000,
//          4000,
//          1000,
//          testDateEnd);
//
//      Symbol mockSymbol = new Symbol("GOOG");
//
//      entityManager.persist(mockSymbol);
//
//      List<Quote> mockQuotes = Arrays.asList(
//          new Quote(mockSymbol, 1000, 100, testDateStart),
//          new Quote(mockSymbol, 2000, 200, testDateStart),
//          new Quote(mockSymbol, 3000, 300, testDateEnd),
//          new Quote(mockSymbol, 4000, 400, testDateEnd)
//      );
//
//      mockQuotes.forEach(quote -> entityManager.persist(quote));
//
//      when(quoteRepository.getAggregateData(anyInt(), any(Date.class), any(Date.class))).thenReturn(expected);
//
//
//
//      assertThat(quote, is(equalTo(expected)));
//
//      assertThat(quote.getSymbol(), is(equalTo("GOOG")));
//      assertThat(quote.getMaxPrice(), is(equalTo(1130.99)));
//      assertThat(quote.getMinPrice(), is(equalTo(1120.01)));
//      assertThat(quote.getClosingPrice(), is(equalTo(1122.57)));
//      assertThat(quote.getTotalVolume(), is(equalTo(724223)));
//      assertThat(quote.getDate().toString(), is(equalTo("2018-06-22T16:30:00.000+0000")));
//    } catch (ParseException e) {
//      e.printStackTrace();
//    }

    mockMvc.perform(get("/daily/GOOG/2016-06-22")).andExpect(status().isOk()).andReturn();
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
  public void testGetAggregateMonthly() throws Exception {
//    try {
//      AggregateQuote expected = new AggregateQuote(
//          "GOOG",
//          1130.99,
//          1120,
//          1129.65,
//          2159363,
//          new Date(1530048600));
//
//      AggregateQuote quote = quoteController.getAggregateMonthly("GOOG", "2018-06");
//
//      assertThat(quote, is(equalTo(expected)));
//
//      assertThat(quote.getSymbol(), is(equalTo("GOOG")));
//      assertThat(quote.getMaxPrice(), is(equalTo(1130.99)));
//      assertThat(quote.getMinPrice(), is(equalTo(1120)));
//      assertThat(quote.getClosingPrice(), is(equalTo(1129.65)));
//      assertThat(quote.getTotalVolume(), is(equalTo(2159363)));
//      assertThat(quote.getDate().toString(), is(equalTo("2018-06-26T16:30:00.000+0000")));
//    } catch (ParseException e) {
//      e.printStackTrace();
//    }
    mockMvc.perform(get("/monthly/GOOG/2016-06")).andExpect(status().isOk()).andReturn();
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