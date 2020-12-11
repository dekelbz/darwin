package com.dekel.darwin.users.service;

import com.dekel.darwin.users.domain.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.thavam.util.concurrent.blockingMap.BlockingMap;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String userSaveTopic;
    private final String userGetTopic;
    private final BlockingMap<String, UserDTO> usersToConsume;
    private final String userDeleteTopic;

    public UserServiceImpl(KafkaTemplate<String, Object> kafkaTemplate,
                           @Value("${kafka.topic.save.user}") String userSaveTopic,
                           @Value("${kafka.topic.get.user}") String userGetTopic,
                           BlockingMap<String, UserDTO> usersToConsume,
                           @Value("${kafka.topic.delete.user}") String userDeleteTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.userSaveTopic = userSaveTopic;
        this.userGetTopic = userGetTopic;
        this.usersToConsume = usersToConsume;
        this.userDeleteTopic = userDeleteTopic;
    }

    @Override
    public void saveOrUpdate(UserDTO user) {
        kafkaTemplate.send(userSaveTopic, user);
    }

    @Override
    public Optional<UserDTO> getByEmail(String email) throws InterruptedException {
        kafkaTemplate.send(userGetTopic, email);
        return Optional.ofNullable(pollUser(email));
    }

    private UserDTO pollUser(String email) throws InterruptedException {
        return usersToConsume.take(email, 10, TimeUnit.SECONDS);
    }

    @Override
    public void deleteByEmail(String email) {
        kafkaTemplate.send(userDeleteTopic, email);
    }




}
