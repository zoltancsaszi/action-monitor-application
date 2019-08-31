package net.zoltancsaszi.actionmonitor.controller;

import net.zoltancsaszi.actionmonitor.controller.exception.WalletNameException;
import net.zoltancsaszi.actionmonitor.controller.exception.WalletNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.json.Json;

/**
 * Rest controller advice to handle exceptions.
 *
 * @author Zoltan Csaszi
 */
@RestControllerAdvice
class WalletNotFoundAdvice {

    @ExceptionHandler(WalletNotFoundException.class)
    public final ResponseEntity handleWalletNotFoundException(WalletNotFoundException ex) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        String body = Json.createObjectBuilder()
                .add("message", "No wallet found with the given ID.")
                .add("statusCode", httpStatus.value())
                .build()
                .toString();

        return new ResponseEntity(body, httpHeaders, httpStatus);
    }

    @ExceptionHandler(WalletNameException.class)
    public final ResponseEntity handleWalletNameException(WalletNameException ex) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        String body = Json.createObjectBuilder()
                .add("message", ex.getMessage())
                .add("statusCode", httpStatus.value())
                .build()
                .toString();

        return new ResponseEntity(body, httpHeaders, httpStatus);
    }
}