package com.solstice.microstocks.data;

import java.util.Date;

public class AggregateQuote {

  private String symbol;
  private double maxPrice;
  private double minPrice;
  private double closingPrice;
  private int totalVolume;
  private Date date;

  public AggregateQuote(String symbol, double maxPrice, double minPrice, double closingPrice, int totalVolume,
      Date date) {
    this.symbol = symbol;
    this.maxPrice = maxPrice;
    this.minPrice = minPrice;
    this.closingPrice = closingPrice;
    this.totalVolume = totalVolume;
    this.date = date;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public double getMaxPrice() {
    return maxPrice;
  }

  public void setMaxPrice(double maxPrice) {
    this.maxPrice = maxPrice;
  }

  public double getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(double minPrice) {
    this.minPrice = minPrice;
  }

  public double getClosingPrice() {
    return closingPrice;
  }

  public void setClosingPrice(double closingPrice) {
    this.closingPrice = closingPrice;
  }

  public int getTotalVolume() {
    return totalVolume;
  }

  public void setTotalVolume(int totalVolume) {
    this.totalVolume = totalVolume;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof AggregateQuote)) {
      return false;
    }

    AggregateQuote other = (AggregateQuote) obj;

    if(!symbol.equals(other.symbol)) {
      return false;
    }
    if (maxPrice != other.maxPrice) {
      return false;
    }
    if (minPrice != other.minPrice) {
      return false;
    }
    if (closingPrice != other.closingPrice) {
      return false;
    }
    if (totalVolume != other.totalVolume) {
      return false;
    }
    return !date.equals(other.date);
  }
}
