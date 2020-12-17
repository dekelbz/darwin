package com.dekel.darwin.users.service;

import com.dekel.darwin.users.domain.UserDTO;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

public interface UserService {
    void saveOrUpdate(UserDTO user);

    UserDTO getByEmail(String email) throws ExecutionException, InterruptedException;

    void deleteByEmail(String email);

    Collection<UserDTO> getAll(int pageNumber);
}
