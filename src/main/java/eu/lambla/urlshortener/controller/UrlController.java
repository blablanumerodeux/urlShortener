package eu.lambla.urlshortener.controller;

import eu.lambla.urlshortener.exception.BusinessException;
import eu.lambla.urlshortener.exception.ValidationException;
import eu.lambla.urlshortener.services.UrlService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@Validated
public class UrlController {

    private UrlService urlService;
    private Map<Integer, URL> db;

    @PostConstruct
    public void init() {
        log.info("after constructor");
        db = urlService.deserialize();
    }

    //if we're doing an api, this should be a POST
    @GetMapping("/api/url")
    public String shortUrl(@RequestParam String url) throws MalformedURLException {
        log.info(url);
        var urlValid = new URL(url);
        // Math.abs( to respect the constraint of 10chars -> only 2147483647 values possible
        db.put(Math.abs(urlValid.hashCode()), urlValid);
        urlService.serialize(db);
        log.info("short : " + Math.abs(urlValid.hashCode()));
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
