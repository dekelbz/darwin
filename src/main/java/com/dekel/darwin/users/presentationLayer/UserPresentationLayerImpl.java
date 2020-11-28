package com.dekel.darwin.users.presentationLayer;

import com.dekel.darwin.users.domain.User;
import com.dekel.darwin.users.domain.UserDTO;
import com.dekel.darwin.users.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPresentationLayerImpl implements UserPresentationLayer {

    private final UserService userService;

    public UserPresentationLayerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void saveOrUpdate(UserDTO user) {
        userService.saveOrUpdate(transform(user));
    }

    @Override
    public Optional<UserDTO> getByEmail(String email) {
        return Optional.ofNullable(userService.getByEmail(email))
                .map(this::transform);
    }

    @Override
    public boolean deleteByEmail(String email) {
        return userService.deleteByEmail(email);
    }

    private UserDTO transform(User dbUser) {
        return UserDTO.builder()
                .email(dbUser.getEmail())
                .firstName(dbUser.getFirstName())
                .lastName(dbUser.getLastName())
                .password(dbUser.getPassword())
                .phoneNumber(dbUser.getPhoneNumber())
                .department(dbUser.getDepartment())
                .roleTitle(dbUser.getRoleTitle())
                .build();
    }

    private User transform(UserDTO userDTO) {
        User dbUser = new User();
        dbUser.setEmail(userDTO.getEmail());
        dbUser.setFirstName(userDTO.getFirstName());
        dbUser.setLastName(userDTO.getLastName());
        dbUser.setPassword(userDTO.getPassword());
        dbUser.setPhoneNumber(userDTO.getPhoneNumber());
        dbUser.setDepartment(userDTO.getDepartment());
        dbUser.setRoleTitle(userDTO.getRoleTitle());
        return dbUser;
    }
}
