package eu.lambla.urlshortener.controller;


import eu.lambla.urlshortener.exception.BusinessException;
import eu.lambla.urlshortener.exception.ValidationException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers {

    /**
     * should return a proper httpstatus code and json ISO Problem with the correct mime type
     * @param ex
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = {BusinessException.class})
    public String notFound(Exception ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(),"{\"Problem\": \"short URL n'existe pas dans la db.\"}");
        log.error("short URL n'existe pas dans la db.");
        return "databaseError";
    }


    @ExceptionHandler(value = {ValidationException.class})
    public String misformed(Exception ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "{\"Problem\": "+ex.getMessage()+"}");
        log.error("URL mal formee");
        return "databaseError";
    }

    @ExceptionHandler(value = {MalformedURLException.class, MethodArgumentTypeMismatchException.class, URISyntaxException.class})
    public String validation(Exception ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "{\"Problem\": \"URL mal formee\"}");
        log.error("URL mal formee");
        return "databaseError";
    }
}
