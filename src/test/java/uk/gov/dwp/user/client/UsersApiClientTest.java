package uk.gov.dwp.user.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import feign.RetryableException;
import uk.gov.dwp.user.client.UsersApiClientTest.LocalRibbonClientConfiguration;
import uk.gov.dwp.user.model.UserDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {LocalRibbonClientConfiguration.class})
@EnableAutoConfiguration
public class UsersApiClientTest {

  @Rule
  public WireMockRule wiremock = new WireMockRule(wireMockConfig().port(18090));

  @BeforeClass
  public static void setProperties() {
    System.setProperty("users-api.ribbon.listOfServers", "127.0.0.1:18090");
  }

  @Before
  public void initTest() {
    LocalRibbonClientConfiguration.server.setPort(this.wiremock.port());
  }

  @TestConfiguration
  public static class LocalRibbonClientConfiguration {

    private static final Server server = new Server("127.0.0.1", 0);

    @Bean
    public ServerList<Server> ribbonServerList() {
      return new StaticServerList<>(server);
    }
  }

  private final int RETRY_COUNT = 3;

  @Autowired
  private UsersApiClient client;

  @Test
  public void testGetUsersInLondon_whenCalled_endpointHit() {
    // given
    wiremock.stubFor(get(urlEqualTo("/city/London/users")).willReturn(aResponse().withStatus(200)));

    // when
    client.getUsersInLondon();

    // then
    verify(1, getRequestedFor(urlEqualTo("/city/London/users")));
  }

  @Test
  public void testGetUsersInLondon_whenCalled_ThenReturnWithUsers() {
    // given
    final UserDTO user = UserDTO.builder().id(1L).firstName("Samba").lastName("Mitra")
        .email("abc@def.com").ipAddress("1.2.3.4").latitude(10.45).longitude(45.34).build();

    wiremock.stubFor(get(urlEqualTo("/city/London/users")).willReturn(okJson(
        "[{\"id\":1,\"firstName\":\"Samba\",\"lastName\":\"Mitra\",\"email\":\"abc@def.com\",\"ipAddress\":\"1.2.3.4\",\"latitude\":10.45,\"longitude\":45.34}]")));

    // when
    final List<UserDTO> usersInLondon = client.getUsersInLondon();

    // then
    assertEquals(1, usersInLondon.size());
    assertEquals(user.getId(), usersInLondon.get(0).getId());
  }

  @Test
  public void testGetAllUsers_whenCalled_endpointHit() {
    // given
    wiremock.stubFor(get(urlEqualTo("/users")).willReturn(aResponse().withStatus(200)));

    // when
    client.getAllUsers();

    // then
    verify(1, getRequestedFor(urlEqualTo("/users")));
  }

  @Test
  public void testGetAllUsers_whenCalled_ThenReturnWithUsers() {
    // given
    final UserDTO user = UserDTO.builder().id(1L).firstName("Samba").lastName("Mitra")
        .email("abc@def.com").ipAddress("1.2.3.4").latitude(10.45).longitude(45.34).build();

    wiremock.stubFor(get(urlEqualTo("/users")).willReturn(okJson(
        "[{\"id\":1,\"firstName\":\"Samba\",\"lastName\":\"Mitra\",\"email\":\"abc@def.com\",\"ipAddress\":\"1.2.3.4\",\"latitude\":10.45,\"longitude\":45.34}]")));

    // when
    final List<UserDTO> usersInLondon = client.getAllUsers();

    // then
    assertEquals(1, usersInLondon.size());
    assertEquals(user.getId(), usersInLondon.get(0).getId());
  }

  @Test
  public void test_whenTimeout_thenRetryCorrectAmount() {
    // given
    wiremock.stubFor(
        get(urlEqualTo("/users")).willReturn(aResponse().withStatus(400).withFixedDelay(2000)));

    // when
    try {
      client.getAllUsers();
    } catch (final RetryableException e) {
    }

    // then
    verify(RETRY_COUNT + 1, getRequestedFor(urlEqualTo("/users")));
  }
}
