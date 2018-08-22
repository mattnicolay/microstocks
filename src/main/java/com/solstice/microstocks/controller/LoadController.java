package com.solstice.microstocks.controller;

import com.solstice.microstocks.data.Quote;
import com.solstice.microstocks.service.LoadUtilService;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load")
public class LoadController {

  private LoadUtilService loadUtilService;

  public LoadController(LoadUtilService loadUtilService) {
    this.loadUtilService = loadUtilService;
  }

  @PostMapping
  public ResponseEntity<List<Quote>> load(){
    List<Quote> quotes = null;
    HttpStatus status = HttpStatus.CREATED;
    try {
      quotes = loadUtilService.loadQuotes();
    } catch (IOException e) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return new ResponseEntity<>(quotes, status);
  }

  @GetMapping
  public ResponseEntity<List<Quote>> getStocks() {
    List<Quote> quotes = loadUtilService.findAll();
    return new ResponseEntity<>(
        quotes,
        new HttpHeaders(),
        quotes.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
  }
}
