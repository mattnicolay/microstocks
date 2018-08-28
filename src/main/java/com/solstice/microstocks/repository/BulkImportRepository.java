package com.solstice.microstocks.repository;

import com.solstice.microstocks.model.Quote;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BulkImportRepository extends SimpleJpaRepository<Quote, Integer> {

  private EntityManager entityManager;

  @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
  private int batchSize;

  public BulkImportRepository(EntityManager entityManager) {
    super(Quote.class, entityManager);
    this.entityManager=entityManager;
  }

  @Transactional
  public List<Quote> bulkSave(List<Quote> entities) {
    final List<Quote> savedEntities = new ArrayList<Quote>(entities.size());
    int i = 0;
    for (Quote quote : entities) {
      savedEntities.add(persistOrMerge(quote));
      i++;
      if (i > 0 && i % batchSize == 0) {
        // Flush a batch of inserts and release memory.
        entityManager.flush();
        entityManager.clear();
      }
    }
    return savedEntities;
  }

  private Quote persistOrMerge(Quote quote) {
    if (quote.getId() == 0) {
      entityManager.persist(quote);
      return quote;
    } else {
      return entityManager.merge(quote);
    }
  }
}