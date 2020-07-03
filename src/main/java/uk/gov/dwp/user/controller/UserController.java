package uk.gov.dwp.user.controller;

import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import uk.gov.dwp.user.model.UserDTO;
import uk.gov.dwp.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
@Api(value = "/api/v1/users", tags = {"Users API"}, protocols = "HTTP")
public class UserController {

  private final UserService userService;

  @GetMapping
  @ApiOperation(value = "Fetches all the users",
      notes = "This endpoint fetches all the users who live in London or within 50 miles of London.",
      response = UserDTO.class, responseContainer = "Set")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Users retrieved"),
      @ApiResponse(code = 500, message = "Error while processing the request")})
  public ResponseEntity<Set<UserDTO>> getUsers() {

    final Set<UserDTO> usersInAndNearLondon = this.userService.fetchUsersLivingInAndNearLondon();

    return new ResponseEntity<>(usersInAndNearLondon, HttpStatus.OK);
  }

}
