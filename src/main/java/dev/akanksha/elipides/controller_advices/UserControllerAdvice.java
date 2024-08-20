package dev.akanksha.elipides.controller_advices;

import dev.akanksha.elipides.exceptions.AuthenticationException;
import dev.akanksha.elipides.exceptions.IntegrityException;
import dev.akanksha.elipides.exceptions.ValueException;
import dev.akanksha.elipides.models.Message;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(IntegrityException.class)
    ResponseEntity<Message> integrityExceptionAdvice(Exception e) {
        return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<List<Message>> constraintViolationExceptionAdvice(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getConstraintViolations().stream().map(violation -> new Message(violation.getPropertyPath().toString() + " " + violation.getMessage())).toList()
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    ResponseEntity<Message> noSuchElementExceptionAdvice(NoSuchElementException e) {
        return new ResponseEntity<>(new Message(e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValueException.class)
    ResponseEntity<Message> valueExceptionAdvice(ValueException e) {
        return new ResponseEntity<>(new Message(e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<Message> authenticaitonUserAdvice(AuthenticationException e) {
        return new ResponseEntity<>(new Message(e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
}
