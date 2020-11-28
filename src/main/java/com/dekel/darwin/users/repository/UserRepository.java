package com.dekel.darwin.users.repository;

import com.dekel.darwin.users.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User getByEmail(String email);

    long deleteByEmail(String email);

    UserId getIdByEmail(String email);

    interface UserId {
        Long getId();
    }
}
