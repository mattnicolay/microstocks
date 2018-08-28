package com.solstice.microstocks.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "symbol-service", fallback = SymbolServiceFallback.class)
public interface SymbolServiceClient {
  @RequestMapping("/symbols/{name}/id")
  int getSymbolIdByName(@PathVariable("name") String name);
}

@Component
class SymbolServiceFallback implements SymbolServiceClient {
  @Override
  public int getSymbolIdByName(String name) {
    return -1;
  }
}
