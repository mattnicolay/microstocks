package com.solstice.microstocks.service;

import com.solstice.microstocks.exception.ImproperDateFormatException;
import com.solstice.microstocks.feign.SymbolServiceClient;
import com.solstice.microstocks.model.AggregateQuote;
import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.model.TimePeriod;
import com.solstice.microstocks.repository.QuoteRepository;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuoteUtilService {


  private QuoteRepository quoteRepository;
  private SymbolServiceClient symbolServiceClient;

  public QuoteUtilService(QuoteRepository quoteRepository,
      SymbolServiceClient symbolServiceClient) {
    this.quoteRepository = quoteRepository;
    this.symbolServiceClient= symbolServiceClient;
  }

  public List<Quote> findAll() {
    return quoteRepository.findAll();
  }

  public void deleteAll() {
    quoteRepository.deleteAll();
  }

  public AggregateQuote getAggregate(
      String symbol,
      String dateString,
      TimePeriod timePeriod) throws NullPointerException, DateTimeParseException {
    int symbolId = getIdFromSymbolService(symbol);
    LocalDate fromDate = LocalDate.parse(dateString);
    LocalDate toDate = getNext(timePeriod, fromDate);

    return quoteRepository.getAggregateData(symbolId, fromDate, toDate);
  }

  private int getIdFromSymbolService(String symbol) throws NullPointerException {
    return symbolServiceClient.getSymbolIdByName(symbol);
  }

  private LocalDate getNext(TimePeriod timePeriod, LocalDate date) {
    switch(timePeriod) {
      case DAY:
        return date.plusDays(1);
      case MONTH:
        return date.plusMonths(1);
      default:
        return null;
    }
  }
}
