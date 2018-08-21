package com.solstice.microstocks.service;

import com.solstice.microstocks.data.AggregateQuote;
import com.solstice.microstocks.data.TimePeriod;
import com.solstice.microstocks.repository.QuoteRepository;
import com.solstice.microstocks.repository.SymbolRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.stereotype.Service;

@Service
public class QuoteUtilService {

  private final long ONE_DAY_IN_MILLISECONDS = 86400000L;

  private QuoteRepository quoteRepository;
  private SymbolRepository symbolRepository;

  public QuoteUtilService(QuoteRepository quoteRepository,
      SymbolRepository symbolRepository) {
    this.quoteRepository = quoteRepository;
    this.symbolRepository = symbolRepository;
  }

  public AggregateQuote getAggregate(
      String symbol,
      String dateString,
      TimePeriod timePeriod,
      String pattern) throws ParseException {

    int symbolId = symbolRepository.findBySymbol(symbol).getId();
    Date fromDate = parseDate(dateString, pattern);
    Date toDate = getNext(timePeriod, fromDate);

    return quoteRepository.getAggregateData(symbolId, fromDate, toDate);
  }

  private Date parseDate(String dateString, String pattern) throws ParseException {
    return new SimpleDateFormat(pattern).parse(dateString);
  }

  private Date getNext(TimePeriod timePeriod, Date date) {
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
