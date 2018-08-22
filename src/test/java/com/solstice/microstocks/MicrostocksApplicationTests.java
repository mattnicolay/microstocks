package com.solstice.microstocks;

import com.solstice.microstocks.controller.LoadControllerIntegrationTest;
import com.solstice.microstocks.controller.QuoteControllerIntegrationTest;
import com.solstice.microstocks.repository.QuoteRepositoryTest;
import com.solstice.microstocks.repository.SymbolRepository;
import com.solstice.microstocks.service.LoadUtilServiceUnitTest;
import com.solstice.microstocks.service.LoadUtilServiceUnitTestFailures;
import com.solstice.microstocks.service.QuoteUtilServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({LoadControllerIntegrationTest.class, QuoteControllerIntegrationTest.class, QuoteRepositoryTest.class,
    SymbolRepository.class, LoadUtilServiceUnitTest.class, LoadUtilServiceUnitTestFailures.class,
    QuoteUtilServiceUnitTest.class})
public class MicrostocksApplicationTests {

}
