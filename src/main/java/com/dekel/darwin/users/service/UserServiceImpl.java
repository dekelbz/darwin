package com.dekel.darwin.users.service;

import com.dekel.darwin.users.domain.User;
import com.dekel.darwin.users.domain.UserDTO;
import com.dekel.darwin.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KafkaTemplate<String, UserDTO> kafkaTemplate;
    private final String userSaveTopic;

    public UserServiceImpl(UserRepository userRepository, KafkaTemplate<String, UserDTO> kafkaTemplate,
                           @Value("${kafka.topic.save.user}") String userSaveTopic) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.userSaveTopic = userSaveTopic;
    }

    @Override
    @Transactional
    public void saveOrUpdate(UserDTO user) {
        kafkaTemplate.send(userSaveTopic, user);
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
