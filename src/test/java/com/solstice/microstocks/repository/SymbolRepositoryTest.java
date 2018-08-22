package com.solstice.microstocks.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import com.solstice.microstocks.data.Symbol;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SymbolRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private SymbolRepository symbolRepository;

  @Test
  public void testFindBySymbol() {
    Symbol symbol = new Symbol("GOOG");
    entityManager.persist(symbol);

    Symbol foundSymbol = symbolRepository.findBySymbol("GOOG");

    assertThat(foundSymbol.getSymbol(), is(equalTo(symbol.getSymbol())));
    assertThat(foundSymbol.getId(), is(equalTo(symbol.getId())));
  }

  @Test
  public void testFindBySymbolNoSymbol() {
    Symbol foundSymbol = symbolRepository.findBySymbol("GOOG");

    assertThat(foundSymbol, is(nullValue()));
  }
}
