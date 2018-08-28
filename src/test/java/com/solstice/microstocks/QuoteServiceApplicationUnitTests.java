package com.solstice.microstocks;

import com.solstice.microstocks.controller.LoadControllerUnitTest;
import com.solstice.microstocks.controller.QuoteControllerUnitTest;
import com.solstice.microstocks.repository.QuoteRepositoryTest;
import com.solstice.microstocks.service.DateServiceUnitTest;
import com.solstice.microstocks.service.LoadUtilServiceUnitTest;
import com.solstice.microstocks.service.QuoteUtilServiceUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    LoadControllerUnitTest.class,
    QuoteControllerUnitTest.class,
    QuoteRepositoryTest.class,
    LoadUtilServiceUnitTest.class,
    QuoteUtilServiceUnitTest.class,
    DateServiceUnitTest.class})
public class QuoteServiceApplicationUnitTests {

}
