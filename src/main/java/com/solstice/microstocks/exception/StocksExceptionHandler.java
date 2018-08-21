package com.solstice.microstocks.exception;

import java.io.IOException;
import java.text.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class StocksExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(value = {IOException.class})
  protected ResponseEntity<Object> handleIOException(Exception ex, WebRequest request) {
    String bodyOfResponse = "<h1>ERROR:</h1>\n "
        + "Encountered error with message: \n"
        + ex.getMessage();
    ex.printStackTrace();
    return handleExceptionInternal(
        ex,
        bodyOfResponse,
        new HttpHeaders(),
        HttpStatus.BAD_REQUEST,
        request);
  }

  @ExceptionHandler(value = {ParseException.class})
  protected ResponseEntity<Object> handleParseException(Exception ex, WebRequest request) {
    String bodyOfResponse = "<h1>ERROR:</h1>\n "
        + "<p>Encountered error with message: </p>\n"
        + ex.getMessage();
    ex.printStackTrace();
    return handleExceptionInternal(
        ex,
        bodyOfResponse,
        new HttpHeaders(),
        HttpStatus.BAD_REQUEST,
        request);
  }

  @ExceptionHandler(value = {NullPointerException.class})
  protected ResponseEntity<Object> handleNullPointerException(Exception ex, WebRequest request) {
    String bodyOfResponse = "<h1>ERROR:</h1>\n"
        + "<p>Encountered null value.</p>\n"
        + "<p>Make sure the database has been populated.";
    ex.printStackTrace();
    return handleExceptionInternal(
        ex,
        bodyOfResponse,
        new HttpHeaders(),
        HttpStatus.INTERNAL_SERVER_ERROR,
        request);
  }

}
