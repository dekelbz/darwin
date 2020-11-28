package com.dekel.darwin.users.repository;

import com.dekel.darwin.users.domain.User;
import com.dekel.darwin.users.repository.views.Id;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User getByEmail(String email);

    long deleteByEmail(String email);

    Id getIdByEmail(String email);

}
