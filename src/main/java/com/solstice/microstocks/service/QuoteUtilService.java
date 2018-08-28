package com.solstice.microstocks.service;

import com.solstice.microstocks.feign.SymbolServiceClient;
import com.solstice.microstocks.model.AggregateQuote;
import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.model.TimePeriod;
import com.solstice.microstocks.repository.QuoteRepository;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class QuoteUtilService {


  private QuoteRepository quoteRepository;
  private SymbolServiceClient symbolServiceClient;
  private DateService dateService;

  public QuoteUtilService(QuoteRepository quoteRepository, SymbolServiceClient symbolServiceClient,
      DateService dateService) {
    this.quoteRepository = quoteRepository;
    this.symbolServiceClient= symbolServiceClient;
    this.dateService = dateService;
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
      TimePeriod timePeriod,
      String pattern) throws NullPointerException, ParseException {

    int symbolId = getIdFromSymbolService(symbol);
    Date fromDate = dateService.parseDate(dateString, pattern);
    Date toDate = dateService.getNext(timePeriod, fromDate);

    return quoteRepository.getAggregateData(symbolId, fromDate, toDate);
  }

  private int getIdFromSymbolService(String symbol) throws NullPointerException {
    return symbolServiceClient.getSymbolIdByName(symbol);
  }


}
