package com.dekel.darwin.users.service;

import com.dekel.darwin.users.domain.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface UserService {
    void saveOrUpdate(UserDTO user);

    Optional<UserDTO> getByEmail(String email) throws ExecutionException, InterruptedException;

    void deleteByEmail(String email);


}
