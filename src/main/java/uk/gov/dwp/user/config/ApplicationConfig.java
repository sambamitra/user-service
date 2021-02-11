package uk.gov.dwp.user.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.URL;

@Configuration
@Slf4j
public class ApplicationConfig {

    @Value("${rest.ssl.trustStore}")
    private String trustStore;

    @Value("${rest.ssl.trustStorePassword}")
    private String trustStorePassword;

    private String protocol = "TLSv1.2";

    public ClientHttpRequestFactory getRequestFactory() {
        final SSLContext sslContext;
        try {
            sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(new URL(trustStore),
                            trustStorePassword.toCharArray())
                    .setProtocol(protocol)
                    .build();
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to setup client SSL context", e
            );
        }

        final HttpClient httpClient = HttpClientBuilder.create()
                .setSSLContext(sslContext)
                .build();

        final ClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        log.info("Registered SSL truststore {} for client requests",
                trustStore);
        return requestFactory;
    }

    @Bean
    @Profile("prod")
    public RestTemplate restTemplate() {
        return new RestTemplate(getRequestFactory());
    }

}
