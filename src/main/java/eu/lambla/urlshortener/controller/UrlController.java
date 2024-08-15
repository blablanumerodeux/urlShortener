package eu.lambla.urlshortener.controller;

import eu.lambla.urlshortener.exception.BusinessException;
import eu.lambla.urlshortener.services.UrlService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
public class UrlController {

    private UrlService urlService;
    private Map<Integer, URL> db;

    @PostConstruct
    public void init() {
        log.info("after constructor");
        db = urlService.deserialize();
    }

    @GetMapping("/api/url")
    public String shortUrl(@RequestParam String url) throws MalformedURLException, URISyntaxException {
        log.info(url);
        var urlValid = new URL(url);
        // Math.abs( to respect the constraint of 10chars -> only 2147483647 values possible
        db.put(Math.abs(urlValid.hashCode()), urlValid);
        urlService.serialize(db);
        return "short : " + Math.abs(urlValid.hashCode());
    }

    @GetMapping("/api/short")
    public String shortUrl(@RequestParam Integer shortUrl) throws MalformedURLException, URISyntaxException, BusinessException {
        log.info(shortUrl.toString());
        URL url = db.get(shortUrl);
        if (url == null)
            throw new BusinessException();
        urlService.displayMap(db);
        return "url : " + url.toString();
    }
}
