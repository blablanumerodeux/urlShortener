package eu.lambla.urlshortener;

import eu.lambla.urlshortener.controller.UrlController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class UrlShortenerApplicationIntegrationTests {

    @Autowired
    private UrlController urlController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void badRequestMissformed() throws Exception {
        Assertions.assertTrue(Objects.requireNonNull(mockMvc.perform(get("/api/url?url=htt//test.ca?tt&url=tt")).andDo(print())
                        .andExpect(status().is4xxClientError())
                        .andReturn()
                        .getResponse()
                        .getErrorMessage())
                .contains("URL mal formee"));
    }

    @Test
    void goodRequestUnshort() throws Exception {
        this.urlController.shortUrl("http://ca.ca");
        Assertions.assertTrue(this.mockMvc.perform(get("/api/short?shortUrl=97579206")).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("url : http://ca.ca"));
    }

    @Test
    void goodRequestUnshortMisformed() throws Exception {
        Assertions.assertEquals(400, this.mockMvc.perform(get("/api/short?shortUrl=xxxx")).andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getStatus());
    }

    @Test
    void goodRequestUnshortNotFound() throws Exception {
        Assertions.assertEquals(404, this.mockMvc.perform(get("/api/short?shortUrl=1419289581")).andDo(print())
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getStatus());
    }

    @Test
    void goodRequest() throws Exception {
        Assertions.assertEquals(200, this.mockMvc.perform(get("/api/url?url=http://ca.ca")).andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getResponse()
                .getStatus());
    }
}
