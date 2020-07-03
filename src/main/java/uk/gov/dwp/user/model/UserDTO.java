package uk.gov.dwp.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "User")
public class UserDTO {

  @ApiModelProperty(position = 1, value = "The user identifier.")
  private Long id;

  @ApiModelProperty(position = 2, value = "User's first name.")
  private String firstName;

  @ApiModelProperty(position = 3, value = "User's last name")
  private String lastName;

  @ApiModelProperty(position = 4, value = "User's email")
  private String email;

  @ApiModelProperty(position = 5, value = "User's ip address")
  private String ipAddress;

  @ApiModelProperty(position = 6, value = "User's latitude")
  private Double latitude;

  @ApiModelProperty(position = 7, value = "User's longitude")
  private Double longitude;

}
