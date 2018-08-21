package com.solstice.microstocks.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solstice.microstocks.data.RawQuote;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.properties")
public class JsonUtilService {

  @Value("${dataset-url}")
  private URL datasetUrl;

  public List<RawQuote> getStocksFromJson() throws IOException {
    ObjectMapper jsonMapper = new ObjectMapper();

    return jsonMapper.readValue(datasetUrl, new TypeReference<List<RawQuote>>(){});
  }
}
