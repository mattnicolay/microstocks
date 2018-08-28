package com.solstice.microstocks.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.springframework.data.domain.Persistable;

@Entity
@SqlResultSetMapping(name="AggregateQuoteMapping", classes = {
    @ConstructorResult(targetClass = AggregateQuote.class,
        columns = {
            @ColumnResult(name="name", type=String.class),
            @ColumnResult(name="maxPrice", type=Double.class),
            @ColumnResult(name="minPrice", type=Double.class),
            @ColumnResult(name="closingPrice", type=Double.class),
            @ColumnResult(name="totalVolume", type=Integer.class),
            @ColumnResult(name="date", type=Date.class)
        })
})
@NamedNativeQuery(
    name = "Quote.getAggregateData",
    query = "SELECT s.name, maxPrice, minPrice, totalVolume, closingPrice, date\n"
        + "FROM symbol s,\n"
        + "(SELECT symbol_id, MAX(price) AS maxPrice, MIN(price) AS minPrice, SUM(volume) AS totalVolume\n"
        + "FROM quote\n"
        + "WHERE symbol_id = :symbolId AND date BETWEEN :fromDate AND :toDate\n) s1, \n"
        + "(SELECT price AS closingPrice, date\n"
        + "FROM quote\n"
        + "WHERE symbol_id = :symbolId AND date < :toDate\n"
        + "ORDER BY date DESC\n"
        + "LIMIT 1) s2\n"
        + "WHERE s1.symbol_id = s.id",
    resultSetMapping = "AggregateQuoteMapping"
)
public class Quote{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @JsonProperty("symbol")
  private long symbolId;
  private double price;
  private int volume;
  @Temporal(TemporalType.TIMESTAMP)
  private Date date;


  public Quote() {
  }

  public Quote(long symbolId, double price, int volume, Date date) {
    this.symbolId = symbolId;
    this.price = price;
    this.volume = volume;
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public long getSymbolId() {
    return symbolId;
  }

  public void setSymbolId(long symbolId) {
    this.symbolId = symbolId;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getVolume() {
    return volume;
  }

  public void setVolume(int volume) {
    this.volume = volume;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Quote)) {
      return false;
    }

    Quote quote = (Quote) obj;
    if (symbolId != quote.symbolId) {
      return false;
    }
    if (price != quote.price) {
      return false;
    }
    if (volume != quote.volume) {
      return false;
    }
    return date.equals(quote.date);
  }
}
