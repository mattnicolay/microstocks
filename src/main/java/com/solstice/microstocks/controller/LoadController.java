package com.solstice.microstocks.controller;

import com.solstice.microstocks.data.Quote;
import com.solstice.microstocks.repository.QuoteRepository;
import com.solstice.microstocks.service.UtilService;
import com.solstice.microstocks.service.JsonUtilService;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load")
public class LoadController {

  private JsonUtilService jsonUtilService;
  private UtilService utilService;
  private QuoteRepository quoteRepository;

//  public LoadController(JsonUtilService jsonUtilService, UtilService utilService,
//      QuoteRepository quoteRepository) {
//    this.jsonUtilService = jsonUtilService;
//    this.utilService = utilService;
//    this.quoteRepository = quoteRepository;
//  }

  public LoadController(UtilService utilService) {
    this.utilService = utilService;
  }

  @PostMapping
  public ResponseEntity<List<Quote>> load() throws IOException {
    List<Quote> quotes = utilService.loadQuotes();
    return new ResponseEntity<>(quotes, HttpStatus.CREATED);
  }

  @GetMapping
  public List<Quote> getStocks() {
    return (List<Quote>) utilService.findAll();
  }
}
