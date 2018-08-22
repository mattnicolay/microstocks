package com.solstice.microstocks.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.solstice.microstocks.data.AggregateQuote;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:test-dataset.xml")
public class QuoteRepositoryTest {

  @Autowired
  private QuoteRepository quoteRepository;

  @Test
  public void testGetAggregateData() {
    try{
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date fromDate = simpleDateFormat.parse("2018-06-22");
      Date toDate =simpleDateFormat.parse("2018-06-23");

      AggregateQuote expected = new AggregateQuote(
          "GOOG",
          6666.66,
          1111.11,
          6666.66,
          666,
          fromDate
      );

      AggregateQuote aggregateQuote = quoteRepository.getAggregateData(2, fromDate, toDate);


      assertThat(aggregateQuote, is(notNullValue()));
      assertThat(aggregateQuote.getSymbol(), is(equalTo(expected.getSymbol())));
      assertThat(aggregateQuote.getMaxPrice(), is(equalTo(expected.getMaxPrice())));
      assertThat(aggregateQuote.getMinPrice(), is(equalTo(expected.getMinPrice())));
      assertThat(aggregateQuote.getClosingPrice(), is(equalTo(expected.getClosingPrice())));
      assertThat(aggregateQuote.getTotalVolume(), is(equalTo(expected.getTotalVolume())));
      //use simpleDateFormat to make sure dates are same format
      assertThat(simpleDateFormat.format(aggregateQuote.getDate()),
          is(equalTo(simpleDateFormat.format(expected.getDate()))));
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }
}
