package uk.gov.dwp.user.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlingControllerAdvice {

    /**
     * Handles exceptions from API call and maps to appropriate HTTP status code.
     *
     * @param exception - the exception
     * @return HTTP status
     */
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<Void> handleApiClientException(final HttpStatusCodeException exception) {
        log.error("Error while calling the API, status code was - {} and status text was - {}", exception.getStatusCode(), exception.getStatusText());
        log.error("Exception was - ", exception);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

