package com.dekel.darwin.users.service;

import com.dekel.darwin.users.domain.User;
import com.dekel.darwin.users.domain.UserDTO;

public interface UserService {
    void saveOrUpdate(UserDTO user);

    User getByEmail(String email);

    boolean deleteByEmail(String email);
}
