package uk.gov.dwp.user.config;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ApplicationConfig {

  public ClientHttpRequestFactory getRequestFactory() {
    final CloseableHttpClient httpClient =
        HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
    final HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);
    return requestFactory;
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate(getRequestFactory());
  }

}
