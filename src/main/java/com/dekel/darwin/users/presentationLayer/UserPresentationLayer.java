package com.dekel.darwin.users.presentationLayer;

import com.dekel.darwin.users.domain.UserDTO;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.Optional;

public interface UserPresentationLayer {
    void saveOrUpdate(UserDTO user);

    Optional<UserDTO> getByEmail(String email);

    boolean deleteByEmail(String email);

    Collection<String> generateErrorResponse(BindingResult bindingResult);
}
