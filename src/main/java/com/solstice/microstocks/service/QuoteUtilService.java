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

  private final long ONE_DAY_IN_MILLISECONDS = 86400000L;

  private QuoteRepository quoteRepository;
  private RestTemplate restTemplate;
  private EurekaClient eurekaClient;

  public QuoteUtilService(QuoteRepository quoteRepository,
      RestTemplate restTemplate, EurekaClient eurekaClient) {
    this.quoteRepository = quoteRepository;
    this.restTemplate = restTemplate;
    this.eurekaClient = eurekaClient;
  }

  public AggregateQuote getAggregate(
      String symbol,
      String dateString,
      TimePeriod timePeriod,
      String pattern) throws NullPointerException, ParseException {

    int symbolId = getIdFromSymbolService(symbol);
    Date fromDate = parseDate(dateString, pattern);
    Date toDate = getNext(timePeriod, fromDate);

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

  private Date parseDate(String dateString, String pattern) throws ParseException {
    return new SimpleDateFormat(pattern).parse(dateString);
  }

  private Date getNext(TimePeriod timePeriod, Date date) {
    switch(timePeriod) {
      case DAY:
        return getNextDay(date);
      case MONTH:
        return getNextMonth(date);
      default:
        return null;
    }
  }

  private Date getNextDay(Date date) {
    return new Date(date.getTime() + ONE_DAY_IN_MILLISECONDS);
  }

  private Date getNextMonth(Date date) {
    Calendar calendar = new GregorianCalendar();
    calendar.setTime(date);
    long monthOffset = ONE_DAY_IN_MILLISECONDS * calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    return new Date(date.getTime() + monthOffset);
  }
}
