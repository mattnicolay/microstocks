package com.solstice.microstocks.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.repository.QuoteRepository;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoadUtilService {

  private QuoteRepository quoteRepository;

  @Value("${dataset-url}")
  private URL datasetUrl;

  public LoadUtilService(QuoteRepository quoteRepository) {
    this.quoteRepository = quoteRepository;
  }

  public List<Quote> loadQuotes() throws IOException {
    List<Quote> quotes = getStocksFromJson();

    quoteRepository.saveAll(quotes);
    return quotes;
  }

  public List<Quote> findAll() {
    return quoteRepository.findAll();
  }


  private List<Quote> getStocksFromJson() throws IOException {
    ObjectMapper jsonMapper = new ObjectMapper();

    jsonMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS"));

    return jsonMapper.readValue(datasetUrl, new TypeReference<List<Quote>>(){});
  }

}
