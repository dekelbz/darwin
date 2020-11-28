package com.dekel.darwin.users.service;

import com.dekel.darwin.users.domain.User;
import com.dekel.darwin.users.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void saveOrUpdate(User user) {
        Optional.ofNullable(userRepository.getIdByEmail(user.getEmail()))
                .ifPresent(userId -> user.setId(userId.getId()));
        userRepository.save(user);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    @Transactional
    public boolean deleteByEmail(String email) {
        return userRepository.deleteByEmail(email) > 0;
    }


}
