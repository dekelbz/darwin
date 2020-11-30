package com.dekel.darwin.users.presentationLayer;

import com.dekel.darwin.users.domain.User;
import com.dekel.darwin.users.domain.UserDTO;
import com.dekel.darwin.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserPresentationLayerImplTest {

    private UserPresentationLayer userPresentationLayer;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        userPresentationLayer = new UserPresentationLayerImpl(userService);
    }

    @Test
    public void shouldSave() {
        //given
        UserDTO userDTO = UserDTO.builder()
                .department("User Department")
                .email("user@email.com")
                .build();

        //when
        userPresentationLayer.saveOrUpdate(userDTO);

        //then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveOrUpdate(userCaptor.capture());

        User dbUser = userCaptor.getValue();
        assertEquals(dbUser.getDepartment(), userDTO.getDepartment());
        assertEquals(dbUser.getEmail(), userDTO.getEmail());
    }

    @Test
    public void shouldGetByEmail_existingUser() {
        //given
        User dbUser = new User();
        dbUser.setFirstName("user first");
        dbUser.setLastName("user last");

        String userEmail = "user@email.com";
        given(userService.getByEmail(userEmail))
                .willReturn(dbUser);

        //when
        Optional<UserDTO> optionalUserDTO = userPresentationLayer.getByEmail(userEmail);

        //then
        assertTrue(optionalUserDTO.isPresent());
        UserDTO userDTO = optionalUserDTO.get();
        assertEquals(userDTO.getFirstName(), dbUser.getFirstName());
        assertEquals(userDTO.getLastName(), dbUser.getLastName());
    }

    @Test
    public void shouldNotGetByEmail_missingUser() {
        //given
        String userEmail = "user@email.com";

        //when
        Optional<UserDTO> byEmail = userPresentationLayer.getByEmail(userEmail);

        //then
        assertFalse(byEmail.isPresent());
        verify(userService).getByEmail(userEmail);
    }

    @Test
    public void shouldGenerateErrorResponse() {
        //given
        String errorCode1 = "error code 1";
        String errorCode2 = "error code 2";
        BindingResult bindingResult = mock(BindingResult.class);
        given(bindingResult.getAllErrors())
                .willReturn(Arrays.asList(
                        new ObjectError("name 1", errorCode1),
                        new ObjectError("name 2", errorCode2)));

        //when
        Collection<String> errorCodes = userPresentationLayer.generateErrorResponse(bindingResult);

        //then
        assertThat(errorCodes, hasItems(errorCode1, errorCode2));
        assertEquals(errorCodes.size(), 2);
    }
}