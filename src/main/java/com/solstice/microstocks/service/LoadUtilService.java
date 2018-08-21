package com.solstice.microstocks.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solstice.microstocks.data.Quote;
import com.solstice.microstocks.data.RawQuote;
import com.solstice.microstocks.data.Symbol;
import com.solstice.microstocks.repository.QuoteRepository;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LoadUtilService {

  private QuoteRepository quoteRepository;

  public LoadUtilService(QuoteRepository quoteRepository) {
    this.quoteRepository = quoteRepository;
  }

  @Value("${dataset-url}")
  private URL datasetUrl;


  public List<Quote> loadQuotes() throws IOException {
    List<RawQuote> rawQuotes = getStocksFromJson();
    List<Symbol> symbols = new ArrayList<>();
    List<Quote> quotes = new ArrayList<>();

    for (RawQuote rawQuote : rawQuotes) {
      Symbol symbol = getSymbol(rawQuote.getSymbol(), symbols);
      Quote quote = new Quote(symbol, rawQuote.getPrice(), rawQuote.getVolume(), rawQuote.getDate());
      quotes.add(quote);
    }

    quoteRepository.saveAll(quotes);
    return quotes;
  }


  public List<Quote> findAll() {
    return (List<Quote>) quoteRepository.findAll();
  }

  private Symbol getSymbol(String symbolString, List<Symbol> symbols) {
    for (Symbol symbol : symbols) {
      if (symbol.getSymbol().equals(symbolString)) {
        return symbol;
      }
    }
    Symbol newSymbol = new Symbol(symbolString);
    symbols.add(newSymbol);
    return newSymbol;
  }


  private List<RawQuote> getStocksFromJson() throws IOException {
    ObjectMapper jsonMapper = new ObjectMapper();

    return jsonMapper.readValue(datasetUrl, new TypeReference<List<RawQuote>>(){});
  }

}
