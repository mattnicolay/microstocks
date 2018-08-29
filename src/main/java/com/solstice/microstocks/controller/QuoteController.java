package com.solstice.microstocks.controller;

import com.solstice.microstocks.model.AggregateQuote;
import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.model.TimePeriod;
import com.solstice.microstocks.service.QuoteUtilService;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

  private QuoteUtilService quoteUtilService;

  public QuoteController(QuoteUtilService quoteUtilService) {
    this.quoteUtilService = quoteUtilService;
  }

  @GetMapping("/daily/{symbol}/{dateString}")
  public AggregateQuote getAggregateDaily(
      @PathVariable String symbol,
      @PathVariable String dateString){

    return quoteUtilService.getAggregate(symbol, dateString, TimePeriod.DAY);
  }

  @GetMapping("/monthly/{symbol}/{dateString}")
  public AggregateQuote getAggregateMonthly(
      @PathVariable String symbol,
      @PathVariable String dateString){

    return quoteUtilService.getAggregate(symbol, dateString+"-01", TimePeriod.MONTH);
  }

  @GetMapping
  public ResponseEntity<List<Quote>> getQuotes() {
    List<Quote> quotes = quoteUtilService.findAll();
    return new ResponseEntity<>(
        quotes,
        new HttpHeaders(),
        quotes.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity deleteQuotes() {
    quoteUtilService.deleteAll();
    return new ResponseEntity(new HttpHeaders(), HttpStatus.OK);
  }
}
