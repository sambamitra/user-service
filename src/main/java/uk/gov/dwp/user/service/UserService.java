package uk.gov.dwp.user.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.lucene.util.SloppyMath;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.gov.dwp.user.client.UsersApiClient;
import uk.gov.dwp.user.model.UserDTO;

/**
 * Service class for user related operations.
 * 
 * @author sambamitra
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UsersApiClient usersApiClient;

  /**
   * Fetches all the users who live in London and within 50 miles of London.
   * 
   * @return
   */
  public Set<UserDTO> fetchUsersLivingInAndNearLondon() {
    final List<UserDTO> usersInLondon = this.usersApiClient.getUsersInLondon();
    final List<UserDTO> allUsers = this.usersApiClient.getAllUsers();
    final List<UserDTO> usersNearLondon = new ArrayList<>();
    log.info("Number of users in London : {}", usersInLondon.size());

    for (UserDTO userInLondon : usersInLondon) {
      for (UserDTO user : allUsers) {
        if (user.getId() != userInLondon.getId()) {
          double distanceInMeters = SloppyMath.haversinMeters(userInLondon.getLatitude(),
              userInLondon.getLongitude(), user.getLatitude(), user.getLongitude());
          double distanceInMiles = distanceInMeters * 0.000621371;
          if (distanceInMiles <= 50) {
            usersNearLondon.add(user);
          }
        }
      }
    }

    log.info("Number of users near London : {}", usersNearLondon.size());

    final List<UserDTO> usersInAndNearLondon = Stream
        .concat(usersInLondon.stream(), usersNearLondon.stream()).collect(Collectors.toList());

    return usersInAndNearLondon.stream().collect(
        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(UserDTO::getId))));
  }

}
