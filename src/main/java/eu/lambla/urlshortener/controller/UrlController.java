package eu.lambla.urlshortener.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;

@Slf4j
@RestController
public class Controller {

    @GetMapping()
    public String one(@PathVariable String url) {
        log.info("test");
        return "test";
    }


}
