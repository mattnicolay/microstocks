package com.solstice.microstocks;

import com.solstice.microstocks.controller.LoadControllerIntegrationTest;
import com.solstice.microstocks.controller.LoadControllerUnitTest;
import com.solstice.microstocks.controller.QuoteControllerIntegrationTest;
import com.solstice.microstocks.controller.QuoteControllerUnitTest;
import com.solstice.microstocks.repository.QuoteRepositoryTest;
import com.solstice.microstocks.service.LoadUtilServiceUnitTest;
import com.solstice.microstocks.service.QuoteUtilServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({LoadControllerUnitTest.class, LoadControllerIntegrationTest.class,
    QuoteControllerUnitTest.class, QuoteControllerIntegrationTest.class, QuoteRepositoryTest.class,
    LoadUtilServiceUnitTest.class, QuoteUtilServiceUnitTest.class})
public class MicrostocksApplicationTests {

}
