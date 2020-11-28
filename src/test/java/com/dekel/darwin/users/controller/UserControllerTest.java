package com.dekel.darwin.users.controller;

import com.dekel.darwin.users.domain.UserDTO;
import com.dekel.darwin.users.presentationLayer.UserPresentationLayer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserPresentationLayer userPresentationLayer;

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
        mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .params(getBody(userDTO)))
                .andExpect(status().isOk());

        //then
        ArgumentCaptor<UserDTO> userDTOCaptor = ArgumentCaptor.forClass(UserDTO.class);

        verify(userPresentationLayer).saveOrUpdate(userDTOCaptor.capture());
        assertEquals(userDTOCaptor.getValue().getEmail(), userDTO.getEmail());
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
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .params(getBody(userDTO)))
                .andExpect(status().is4xxClientError())
                .andReturn();

        //then
        List<String> result = objectMapper.readerForListOf(String.class)
                .readValue(mvcResult.getResponse()
                .getContentAsByteArray());
        assertThat(result, hasItem("{error.empty.department}"));

        verifyNoInteractions(userPresentationLayer);
    }

    private MultiValueMap<String, String> getBody(UserDTO userDTO) throws JsonProcessingException {
        return objectMapper.readerFor(Map.class)
                .<Map<String, String>>readValue(objectMapper.writeValueAsString(userDTO))
                .entrySet().stream()
                .collect(LinkedMultiValueMap::new, (map, entry) -> map.add(entry.getKey(), entry.getValue()), (map, entry) -> {});
    }
}