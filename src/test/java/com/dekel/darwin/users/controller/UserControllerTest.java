package com.dekel.darwin.users.controller;

import com.dekel.darwin.users.domain.UserDTO;
import com.dekel.darwin.users.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    public static final String USER_URL = "/user";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    public void shouldSave() throws Exception {
        //given
        UserDTO userDTO = UserDTO.builder()
                .department("user department")
                .email("user@email.com")
                .firstName("user first")
                .lastName("user last")
                .password("user_password")
                .phoneNumber("0543289481")
                .roleTitle("user role title")
                .build();

        //when
        mvc.perform(post(USER_URL)
                .params(getBody(userDTO)))
                .andExpect(status().isOk());

        //then
        ArgumentCaptor<UserDTO> userCaptor = ArgumentCaptor.forClass(UserDTO.class);

        verify(userService).saveOrUpdate(userCaptor.capture());
        assertEquals(userCaptor.getValue().getEmail(), userDTO.getEmail());
    }

    @Test
    public void shouldNotSave_missingDepartment() throws Exception {
        //given
        UserDTO userDTO = UserDTO.builder()
                .email("user@email.com")
                .firstName("user first")
                .lastName("user last")
                .password("user_password")
                .phoneNumber("0543289481")
                .roleTitle("user role title")
                .build();

        //when
        mvc.perform(post(USER_URL)
                .params(getBody(userDTO)))
                .andExpect(status().is4xxClientError());

        //then

    }

    @Test
    public void shouldGetUser() throws Exception {
        //given
        UserDTO user = new UserDTO();
        user.setFirstName("user first name");

        String userEmail = "correct@user.com";
        given(userService.getByEmail(userEmail))
                .willReturn(Optional.of(user));

        UserDTO userDTO = UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName("user last name")
                .build();


        //when + then
        mvc.perform(get(USER_URL)
                .param("email", userEmail))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userDTO)));
    }

    @Test
    public void shouldNotGetUser_notFound() throws Exception {
        //when + then
        mvc.perform(get(USER_URL)
                .param("email", "not@found.com"))
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void shouldDelete() throws Exception {
        //when + then
        mvc.perform(delete(USER_URL)
                .param("email", "user@email.com"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotDelete_notFound() throws Exception {
        //when + then
        mvc.perform(delete(USER_URL)
                .param("email", "user@email.com"))
                .andExpect(status().is4xxClientError());
    }

    private MultiValueMap<String, String> getBody(UserDTO userDTO) throws JsonProcessingException {
        return objectMapper.readerFor(Map.class)
                .<Map<String, String>>readValue(objectMapper.writeValueAsString(userDTO))
                .entrySet().stream()
                .collect(LinkedMultiValueMap::new,
                        (map, entry) -> map.add(entry.getKey(), entry.getValue()),
                        (map, entry) -> {});
    }
}