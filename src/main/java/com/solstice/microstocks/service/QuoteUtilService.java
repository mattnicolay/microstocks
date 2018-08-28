package com.solstice.microstocks.service;


import com.netflix.discovery.EurekaClient;
import com.solstice.microstocks.model.AggregateQuote;
import com.solstice.microstocks.model.TimePeriod;
import com.solstice.microstocks.repository.QuoteRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteUtilService {


  private QuoteRepository quoteRepository;
  private RestTemplate restTemplate;
  private EurekaClient eurekaClient;
  private DateService dateService;

  public QuoteUtilService(QuoteRepository quoteRepository,
      RestTemplate restTemplate, EurekaClient eurekaClient,
      DateService dateService) {
    this.quoteRepository = quoteRepository;
    this.restTemplate = restTemplate;
    this.eurekaClient = eurekaClient;
    this.dateService = dateService;
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
    return restTemplate.getForObject(String.format("%s/symbol/symbols/%s/id", getGatewayUrl(),symbol),
          Integer.class);
  }

  private String getGatewayUrl() {
    return eurekaClient.getNextServerFromEureka("GATEWAY-APPLICATION", false)
        .getHomePageUrl();
  }


}
