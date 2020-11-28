package com.dekel.darwin.users.service;

import com.dekel.darwin.users.domain.User;
import com.dekel.darwin.users.repository.UserRepository;
import com.dekel.darwin.users.repository.views.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void shouldSave_newUser() {
        //given
        String userEmail = "email@bla.com";
        User user = new User();
        user.setEmail(userEmail);

        //when
        userService.saveOrUpdate(user);

        //then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User dbUser = userCaptor.getValue();
        assertEquals(dbUser.getEmail(), userEmail);
        assertNull(dbUser.getId());

        verify(userRepository).getIdByEmail(userEmail);
    }

    @Test
    public void shouldSave_existingUser() {
        //given
        String userEmail = "email@bla.com";

        User oldUser = new User();
        oldUser.setDepartment("old Department");
        oldUser.setId(23L);

        Id userId = mock(Id.class);
        given(userId.getId()).willReturn(oldUser.getId());
        given(userRepository.getIdByEmail(userEmail))
                .willReturn(userId);

        User newUser = new User();
        newUser.setEmail(userEmail);
        newUser.setDepartment("new Department");

        //when
        userService.saveOrUpdate(newUser);

        //then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(savedUser.getId(), oldUser.getId());
        assertEquals(savedUser.getDepartment(), newUser.getDepartment());
    }



}