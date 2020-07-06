package uk.gov.dwp.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.dwp.user.client.UsersApiClient;
import uk.gov.dwp.user.model.UserDTO;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

  @Mock
  private UsersApiClient client;

  @InjectMocks
  private UserService service;

  @Test
  public void shouldFetchUsersLivingInAndNearLondon() {
    // given - 3 users - user1 in london, user2 nearby, user3 far

    UserDTO user1 = UserDTO.builder().id(1L).firstName("Samba").lastName("Mitra")
        .email("abc@def.com").ipAddress("1.2.3.4").latitude(10.45).longitude(45.34).build();
    UserDTO user2 = UserDTO.builder().id(2L).firstName("Abc").lastName("Def").email("abc@def.com")
        .ipAddress("2.3.4.5").latitude(10.40).longitude(45.39).build();
    UserDTO user3 = UserDTO.builder().id(3L).firstName("Xyz").lastName("Zzz").email("abc@def.com")
        .ipAddress("3.4.5.6").latitude(80.40).longitude(96.34).build();

    given(this.client.getUsersInLondon()).willReturn(Arrays.asList(user1));
    given(this.client.getAllUsers()).willReturn(Arrays.asList(user1, user2, user3));

    // when
    final Set<UserDTO> usersInAndNearLondon = this.service.fetchUsersLivingInAndNearLondon();

    // then
    assertEquals(2, usersInAndNearLondon.size());
    final Iterator<UserDTO> iterator = usersInAndNearLondon.iterator();
    assertEquals("Samba", iterator.next().getFirstName());
    assertEquals("Abc", iterator.next().getFirstName());
  }

  @Test
  public void shouldNotFetchAnyUserIfNoOneLivesInOrNearLondon() {
    // given - no users living in or near London
    given(this.client.getUsersInLondon()).willReturn(new ArrayList<>());
    given(this.client.getAllUsers()).willReturn(new ArrayList<>());

    // when
    final Set<UserDTO> usersInAndNearLondon = this.service.fetchUsersLivingInAndNearLondon();

    // then
    assertEquals(0, usersInAndNearLondon.size());
  }

}
