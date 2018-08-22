package com.solstice.microstocks.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.solstice.microstocks.repository.QuoteRepository;
import com.solstice.microstocks.repository.SymbolRepository;
import com.solstice.microstocks.service.QuoteUtilService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(QuoteController.class)
public class QuoteControllerUnitTest {
  @Mock
  private QuoteUtilService quoteUtilService;

  @InjectMocks
  private QuoteController quoteController;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(quoteController).build();
  }

  @Test
  public void testGetAggregateDaily() throws Exception {
    mockMvc.perform(get("/daily/GOOG/2016-06-22")).andExpect(status().isOk()).andReturn();
  }

  @Test
  public void testGetAggregateMonthly() throws Exception {
    mockMvc.perform(get("/monthly/GOOG/2016-06")).andExpect(status().isOk()).andReturn();
  }
}
