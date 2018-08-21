package com.solstice.microstocks.controller;

import com.solstice.microstocks.data.AggregateQuote;
import com.solstice.microstocks.data.TimePeriod;
import com.solstice.microstocks.repository.QuoteRepository;
import com.solstice.microstocks.repository.SymbolRepository;
import com.solstice.microstocks.service.QuoteUtilService;
import java.text.ParseException;
import java.util.Date;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class QuoteController {

  private QuoteUtilService quoteUtilService;

  public QuoteController(QuoteUtilService quoteUtilService) {
    this.quoteUtilService = quoteUtilService;
  }

  @GetMapping("/daily/{symbol}/{dateString}")
  public AggregateQuote getAggregateDaily(
      @PathVariable String symbol,
      @PathVariable String dateString) throws ParseException {

    return quoteUtilService.getAggregate(symbol, dateString, TimePeriod.DAY, "yyyy-MM-dd");
  }

  @GetMapping("/monthly/{symbol}/{dateString}")
  public AggregateQuote getAggregateMonthly(
      @PathVariable String symbol,
      @PathVariable String dateString) throws ParseException {

    return quoteUtilService.getAggregate(symbol, dateString, TimePeriod.MONTH, "yyyy-MM");
  }
}