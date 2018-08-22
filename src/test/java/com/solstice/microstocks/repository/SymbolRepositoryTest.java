package com.solstice.microstocks.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.solstice.microstocks.data.Symbol;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@DatabaseSetup("classpath:test-dataset.xml")
public class SymbolRepositoryTest {

  @Autowired
  private SymbolRepository symbolRepository;

  @Test
  public void testFindBySymbol() {
    Symbol expected = new Symbol("GOOG");
    expected.setId(2);

    Symbol foundSymbol = symbolRepository.findBySymbol("GOOG");

    assertThat(foundSymbol.getSymbol(), is(equalTo(expected.getSymbol())));
    assertThat(foundSymbol.getId(), is(equalTo(expected.getId())));
  }

  @Test
  public void testFindBySymbolNoSymbol() {
    Symbol foundSymbol = symbolRepository.findBySymbol("TEST");

    assertThat(foundSymbol, is(nullValue()));
  }
}
