package uk.gov.dwp.user.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.client.HttpServerErrorException;
import uk.gov.dwp.user.model.UserDTO;
import uk.gov.dwp.user.service.UserService;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService service;

    private static final String BASE_URI = "/api/users/";

    @Test
    public void getUsersShouldReturnOk() throws Exception {
        // given
        final UserDTO user = UserDTO.builder().id(1L).firstName("Samba").lastName("Mitra")
                .email("abc@def.com").ipAddress("1.2.3.4").latitude(10.45).longitude(45.34).build();
        given(this.service.fetchUsersLivingInAndNearLondon())
                .willReturn(new HashSet<>(Arrays.asList(user)));

        // when/then
        this.mockMvc.perform(get(BASE_URI)).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andExpect(content().json(
                "[{\"id\":1,\"firstName\":\"Samba\",\"lastName\":\"Mitra\",\"email\":\"abc@def.com\",\"ipAddress\":\"1.2.3.4\",\"latitude\":10.45,\"longitude\":45.34}]"));
    }

    @Test
    public void getUsersShouldReturnErrorWhenDownstreamApiIsDown() throws Exception {
        // given
        given(this.service.fetchUsersLivingInAndNearLondon())
                .willThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        // when/then
        this.mockMvc.perform(get(BASE_URI)).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isInternalServerError());
    }

}
