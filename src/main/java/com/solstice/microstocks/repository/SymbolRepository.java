package com.solstice.microstocks.repository;

import com.solstice.microstocks.data.Symbol;
import org.springframework.data.repository.CrudRepository;

public interface SymbolRepository extends CrudRepository<Symbol, Long>{
  Symbol findBySymbol(String symbol);
}
