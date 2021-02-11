package uk.gov.dwp.user;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class ApplicationTest {

    @Autowired
    private GenericApplicationContext ctx;

    @Test
    void contextLoads() {
        assertTrue("Context should be running", ctx.isRunning());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Profile("test")
        public RestTemplate testRestTemplate() {
            return new RestTemplate();
        }
    }

}
