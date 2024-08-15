package eu.lambla.urlshortener.controller;


import eu.lambla.urlshortener.exception.BusinessException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers {

    /**
     * we are an API so we should return with correct http status code and json problem like in comments, but for this test we're gonna just reply with a string.
     * @param ex
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(value = {BusinessException.class})
    public String notFound(Exception ex, HttpServletResponse response) throws IOException {
//        response.sendError(HttpStatus.NOT_FOUND.value(),{"Problem": \"short URL n'existe pas dans la db.\"}");
        log.info("short URL n'existe pas dans la db.");
        return "databaseError";
    }

    @ExceptionHandler(value = {MalformedURLException.class, URISyntaxException.class})
    public String misformed(Exception ex, HttpServletResponse response) throws IOException {
//        response.sendError(HttpStatus.BAD_REQUEST.value(), {"Problem": \"URL mal formee\"}");
        log.info("URL mal formee");
        return "databaseError";
    }
}
