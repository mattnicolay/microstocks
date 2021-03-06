package com.solstice.microstocks.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import com.solstice.microstocks.service.LoadUtilService;
import java.io.IOException;
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
@WebMvcTest(LoadController.class)
public class LoadControllerUnitTest {
  @Mock
  private LoadUtilService loadUtilService;

  @InjectMocks
  private LoadController loadController;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(loadController).build();
  }

  @Test
  public void testPostLoad() throws Exception {
    mockMvc.perform(post("/load")).andExpect(status().isCreated());
  }

  @Test
  public void testPostLoadBadJson() throws Exception {
    when(loadUtilService.loadQuotes()).thenThrow(IOException.class);

    mockMvc.perform(post("/load")).andExpect(status().isInternalServerError());
  }
}
