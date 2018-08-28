package com.solstice.microstocks.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import com.solstice.microstocks.model.TimePeriod;
import java.text.ParseException;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class DateServiceTest {
  private DateService dateService = new DateService();

  @Test
  public void testParseDate() {
    Date expected = new Date(1529643600000L);
    Date date = null;
    try {
      date = dateService.parseDate("2018-06-22", "yyyy-MM-dd");
    } catch (ParseException e) {
      fail();
    }
    assertThat(date, is(notNullValue()));
    assertThat(date, is(equalTo(expected)));
  }

  @Test
  public void testParseDateUnparseableDate() {
    try {
      Date date = dateService.parseDate("2018/06/22", "yyyy-MM-dd");

      fail(date.toString());
    } catch (ParseException e) {

    }
  }

  @Test
  public void testGetNextDay() {
    Date expected = new Date(1529643600000L);
    Date fromDate = new Date(1529557200000L);
    Date date = dateService.getNext(TimePeriod.DAY, fromDate);
    assertThat(date, is(notNullValue()));
    assertThat(date, is(equalTo(expected)));
  }

  @Test
  public void testGetNextMonth() {
    Date expected = new Date(1530421200000L);
    Date fromDate = new Date(1527829200000L);
    Date date = dateService.getNext(TimePeriod.MONTH, fromDate);
    assertThat(date, is(notNullValue()));
    assertThat(date, is(equalTo(expected)));
  }
}
