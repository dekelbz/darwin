package com.dekel.darwin.users.listener;

import com.dekel.darwin.users.domain.UserDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.thavam.util.concurrent.blockingMap.BlockingMap;

@Component
public class UserListener {

    private final BlockingMap<String, UserDTO> usersToConsume;

    public UserListener(BlockingMap<String, UserDTO> usersToConsume) {
        this.usersToConsume = usersToConsume;
    }

    @KafkaListener(topics = "${kafka.topic.retrieved.user}")
    public void onRetrievedUser(@Payload(required = false) UserDTO userDTO, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String email) {
        usersToConsume.put(email, userDTO);
    }

}
