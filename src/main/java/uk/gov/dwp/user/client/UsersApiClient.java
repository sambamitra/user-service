package uk.gov.dwp.user.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.gov.dwp.user.model.UserDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsersApiClient {

    private final RestTemplate restTemplate;

    @Value("${remote.users.users-in-london}")
    private String usersInLondonUri;

    @Value("${remote.users.all-users}")
    private String allUsersUri;

    public List<UserDTO> getUsersInLondon() {
        ResponseEntity<List<UserDTO>> response = restTemplate
                .exchange(usersInLondonUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
                });
        return response.getBody();
    }

    public List<UserDTO> getAllUsers() {
        ResponseEntity<List<UserDTO>> response = restTemplate
                .exchange(allUsersUri, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserDTO>>() {
                });
        return response.getBody();
    }

}
