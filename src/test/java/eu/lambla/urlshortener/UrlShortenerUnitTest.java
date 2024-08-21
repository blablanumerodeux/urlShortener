package eu.lambla.urlshortener;

import eu.lambla.urlshortener.services.UrlService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class UrlShortenerUnitTest {

    UrlService urlService;

    @Disabled
    @Test
    void serialize() {
        Map<Integer, URL> db = new HashMap<>();
        urlService.serialize(db);
        //test serialize method?
        // in this context we're not gonna need that
    }

    @Disabled
    @Test
    void deserialize() {
        Map<Integer, URL> db = new HashMap<>();
        Map<Integer, URL> deserialize = urlService.deserialize();
    }
}
