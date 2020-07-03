package uk.gov.dwp.user.client;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import uk.gov.dwp.user.model.UserDTO;

@FeignClient("${remote.users.name}")
public interface UsersApiClient {

  @GetMapping("${remote.users.url.users-in-london}")
  List<UserDTO> getUsersInLondon();

  @GetMapping("${remote.users.url.all-users}")
  List<UserDTO> getAllUsers();

}
