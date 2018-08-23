package com.solstice.microstocks.repository;

import com.solstice.microstocks.model.AggregateQuote;
import com.solstice.microstocks.model.Quote;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface QuoteRepository extends CrudRepository<Quote, Integer> {
  @Query(nativeQuery = true)
  AggregateQuote getAggregateData(
      @Param("symbolId") int symbolId,
      @Param("fromDate") Date fromDate,
      @Param("toDate") Date toDate);

  List<Quote> findAll();
}
