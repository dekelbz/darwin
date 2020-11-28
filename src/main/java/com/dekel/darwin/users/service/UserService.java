package com.dekel.darwin.users.service;

import com.dekel.darwin.users.domain.User;

public interface UserService {
    void saveOrUpdate(User user);

    User getByEmail(String email);

    boolean deleteByEmail(String email);
}
