package com.solstice.microstocks.controller;

import com.solstice.microstocks.repository.QuoteRepository;
import com.solstice.microstocks.service.UtilService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LoadControllerTest {
  @Mock
  private QuoteRepository quoteRepository;

  @InjectMocks
  private UtilService utilService;

  private LoadController loadController = new LoadController(utilService);

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testPostToLoadHappyPath() {

  }
}
