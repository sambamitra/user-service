package uk.gov.dwp.user.controller.advice;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ExceptionHandlingControllerAdviceTest {

    private final ExceptionHandlingControllerAdvice exceptionHandlingControllerAdvice = new ExceptionHandlingControllerAdvice();

    @Test
    public void handleApiClient4xxExceptionShouldReturnInternalServerError() {
        final HttpStatusCodeException exception = new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request");

        final ResponseEntity<Void> errorResponse = exceptionHandlingControllerAdvice.handleApiClientException(exception);

        assertThat(errorResponse.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    @Test
    public void handleApiClient5xxExceptionShouldReturnInternalServerError() {
        final HttpStatusCodeException exception = new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "BPDTS API is down");

        final ResponseEntity<Void> errorResponse = exceptionHandlingControllerAdvice.handleApiClientException(exception);

        assertThat(errorResponse.getStatusCode(), is(equalTo(HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
