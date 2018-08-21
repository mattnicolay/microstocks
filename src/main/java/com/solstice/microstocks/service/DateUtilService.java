package com.solstice.microstocks.service;

import com.solstice.microstocks.data.TimePeriod;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.stereotype.Service;

@Service
public class DateUtilService {

  private final long ONE_DAY_IN_MILLISECONDS = 86400000L;

  public Date parseDate(String dateString, String pattern) throws ParseException {
    return new SimpleDateFormat(pattern).parse(dateString);
  }

  public Date getNext(TimePeriod timePeriod, Date date) {
    switch(timePeriod) {
      case DAY:
        return getNextDay(date);
      case MONTH:
        return getNextMonth(date);
      default:
        return null;
    }
  }

  private Date getNextDay(Date date) {
    return new Date(date.getTime() + ONE_DAY_IN_MILLISECONDS);
  }

  private Date getNextMonth(Date date) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    long monthOffset = ONE_DAY_IN_MILLISECONDS * calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    return new Date(date.getTime() + monthOffset);
  }
}
