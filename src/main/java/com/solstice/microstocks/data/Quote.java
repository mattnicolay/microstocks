package com.solstice.microstocks.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@SqlResultSetMapping(name="AggregateQuoteMapping", classes = {
    @ConstructorResult(targetClass = AggregateQuote.class,
        columns = {
            @ColumnResult(name="symbol", type=String.class),
            @ColumnResult(name="maxPrice", type=Double.class),
            @ColumnResult(name="minPrice", type=Double.class),
            @ColumnResult(name="closingPrice", type=Double.class),
            @ColumnResult(name="totalVolume", type=Integer.class),
            @ColumnResult(name="date", type=Date.class)
        })
})
@NamedNativeQuery(
    name = "Quote.getAggregateData",
    query = "SELECT symbol, maxPrice, minPrice, totalVolume, closingPrice, date\n"
        + "FROM symbol,\n"
        + "(SELECT symbol_id, MAX(price) AS maxPrice, MIN(price) AS minPrice, SUM(volume) AS totalVolume\n"
        + "FROM quote\n"
        + "WHERE symbol_id = :symbolId AND date BETWEEN :fromDate AND :toDate\n) s1, \n"
        + "(SELECT price AS closingPrice, date\n"
        + "FROM quote\n"
        + "WHERE symbol_id = :symbolId AND date < :toDate\n"
        + "ORDER BY date DESC\n"
        + "LIMIT 1) s2\n"
        + "WHERE s1.symbol_id = symbol.id",
    resultSetMapping = "AggregateQuoteMapping"
)
public class Quote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("QuoteId")
  private int id;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name="symbolId", nullable=false)
  private Symbol symbol;
  @Column(nullable = false)
  @JsonProperty("Price")
  private double price;
  @Column(nullable = false)
  @JsonProperty("Volume")
  private int volume;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  @JsonProperty("Date")
  private Date date;


  public Quote() {
  }

  public Quote(Symbol symbol, double price, int volume, Date date) {
    this.symbol = symbol;
    this.price = price;
    this.volume = volume;
    this.date = date;
  }

  public int getId() {
    return id;
  }

  public Symbol getSymbol() {
    return symbol;
  }

  public void setSymbol(Symbol symbol) {
    this.symbol = symbol;
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
}
