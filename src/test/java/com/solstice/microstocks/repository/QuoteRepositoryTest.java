package com.solstice.microstocks.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import com.solstice.microstocks.data.AggregateQuote;
import com.solstice.microstocks.data.Quote;
import com.solstice.microstocks.data.Symbol;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class QuoteRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private QuoteRepository quoteRepository;

  @Test
  public void testGetAggregateData() {
    try{
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date fromDate = simpleDateFormat.parse("2018-06-22");
      Date toDate =simpleDateFormat.parse("2018-06-23");

      Quote quote = new Quote(new Symbol("GOOG"), 1234.56, 789, fromDate);
      entityManager.persist(quote);

      AggregateQuote expected = new AggregateQuote(
          "GOOG",
          1234.56,
          1234.56,
          1234.56,
          1,
          fromDate);

      AggregateQuote aggregateQuote = quoteRepository.getAggregateData(2, fromDate, toDate);

      assertThat(aggregateQuote, is(notNullValue()));
      assertThat(aggregateQuote, is(equalTo(expected)));
    } catch (ParseException e) {
      e.printStackTrace();
    }

  }
}
