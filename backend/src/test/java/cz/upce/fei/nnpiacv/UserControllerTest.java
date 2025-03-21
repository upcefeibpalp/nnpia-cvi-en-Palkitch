package cz.upce.fei.nnpiacv;

import cz.upce.fei.nnpiacv.controller.UserController;
import cz.upce.fei.nnpiacv.domain.User;
import cz.upce.fei.nnpiacv.dto.UserRequestDto;
import cz.upce.fei.nnpiacv.service.UserService;
import exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void testGetUserById() throws Exception {
        User mockUser = new User("john.doe@example.com", "heslo");
        given(userService.findUser(1L)).willReturn(mockUser);

        mockMvc.perform(get("/api/v1/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        given(userService.findUser(1L)).willThrow(new UserNotFoundException("User not found with id: 1"));

        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateUser() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto("john.doe@example.com", "password123");
        User mockUser = new User(1L, "john.doe@example.com", "password123");
        given(userService.createUser(Mockito.any(User.class))).willReturn(mockUser);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\": \"john.doe@example.com\", \"password\": \"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.password").value("password123"));
    }

}
