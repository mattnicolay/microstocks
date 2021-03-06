package com.solstice.microstocks.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solstice.microstocks.model.Quote;
import com.solstice.microstocks.repository.QuoteRepository;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoadUtilService {

  private QuoteRepository quoteRepository;

  @Value("${dataset-url}")
  private URL datasetUrl;

  @Value("${spring.profiles.active}")
  private String profile;

  public LoadUtilService(QuoteRepository quoteRepository) {
    this.quoteRepository = quoteRepository;
  }

  public List<Quote> loadQuotes() throws IOException {
    List<Quote> quotes = getStocksFromJson();

    if (profile.equals("cloud")) {
      int dbCount = (int)quoteRepository.count();
      List<Quote> savedQuotes = new ArrayList<>();


      for (int i = dbCount; savedQuotes.size() < 800 && i < quotes.size(); i++) {
        Quote quote = quotes.get(i);
        quoteRepository.save(quote);
        savedQuotes.add(quote);
      }

      return savedQuotes;
    }

    quoteRepository.saveAll(quotes);
    return quotes;
  }


  private List<Quote> getStocksFromJson() throws IOException {
    ObjectMapper jsonMapper = new ObjectMapper();

    jsonMapper.setDateFormat(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS"));

    return jsonMapper.readValue(datasetUrl, new TypeReference<List<Quote>>(){});
  }

}
