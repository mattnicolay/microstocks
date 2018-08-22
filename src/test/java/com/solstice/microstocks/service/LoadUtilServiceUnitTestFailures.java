package com.solstice.microstocks.service;

import static org.junit.Assert.*;

import com.solstice.microstocks.data.Quote;
import com.solstice.microstocks.repository.QuoteRepository;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, properties = {"dataset-url:https://bootcamp-training-files.cfapps.io/week2/week2-stocks"})
public class LoadUtilServiceUnitTestFailures {

  @Mock
  private QuoteRepository quoteRepository;

  @InjectMocks
  private LoadUtilService loadUtilService;


  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }



}
