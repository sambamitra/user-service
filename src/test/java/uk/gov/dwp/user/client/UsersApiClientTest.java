package uk.gov.dwp.user.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;
import uk.gov.dwp.user.model.UserDTO;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(SpringExtension.class)
@RestClientTest(UsersApiClient.class)
@AutoConfigureWebClient(registerRestTemplate = true)
public class UsersApiClientTest {

    @Autowired
    private UsersApiClient client;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetUsersInLondon_whenCalled_ThenReturnWithUsers() throws JsonProcessingException {
        // given
        final UserDTO user1 = UserDTO.builder().id(1L).firstName("Samba").lastName("Mitra")
                .email("abc@def.com").ipAddress("1.2.3.4").latitude(10.45).longitude(45.34).build();
        final String userInLondonString = objectMapper.writeValueAsString(Arrays.asList(user1));
        this.server.expect(requestTo("http://localhost:18090/city/London/users"))
                .andRespond(withSuccess(userInLondonString, MediaType.APPLICATION_JSON));

        // when
        final List<UserDTO> usersInLondon = this.client.getUsersInLondon();

        // then
        assertEquals(1, usersInLondon.size());
        assertEquals(1L, usersInLondon.get(0).getId());
        assertEquals("Samba", usersInLondon.get(0).getFirstName());
    }

    @Test
    public void testGetUsersInLondon_whenCalled_ThenThrowException() {
        // given
        this.server.expect(requestTo("http://localhost:18090/city/London/users"))
                .andRespond(withServerError());

        // when/then
        assertThrows(RestClientException.class, () -> this.client.getUsersInLondon());
    }

    @Test
    public void testGetAllUsers_whenCalled_ThenReturnWithUsers() throws JsonProcessingException {
        // given
        final UserDTO user1 = UserDTO.builder().id(1L).firstName("Samba").lastName("Mitra")
                .email("abc@def.com").ipAddress("1.2.3.4").latitude(10.45).longitude(45.34).build();
        final UserDTO user2 = UserDTO.builder().id(2L).firstName("Abc").lastName("Def")
                .email("abc@def.com").ipAddress("2.3.4.5").latitude(10.40).longitude(45.39).build();
        final String allUsersString = objectMapper.writeValueAsString(Arrays.asList(user1, user2));
        this.server.expect(requestTo("http://localhost:18090/users"))
                .andRespond(withSuccess(allUsersString, MediaType.APPLICATION_JSON));

        // when
        final List<UserDTO> allUsers = this.client.getAllUsers();

        // then
        assertEquals(2, allUsers.size());
        assertEquals(1L, allUsers.get(0).getId());
        assertEquals("Samba", allUsers.get(0).getFirstName());
        assertEquals(2L, allUsers.get(1).getId());
        assertEquals("Abc", allUsers.get(1).getFirstName());
    }


}
