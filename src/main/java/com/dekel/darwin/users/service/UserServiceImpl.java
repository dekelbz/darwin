package com.dekel.darwin.users.service;

import com.dekel.darwin.users.domain.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final WebClient webClient;

    @Override
    public void saveOrUpdate(UserDTO user) {
        webClient.post()
                .bodyValue(user)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public UserDTO getByEmail(String email) {
        return webClient.get()
                .uri(email)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
    }

    @Override
    public void deleteByEmail(String email) {
        webClient.delete()
                .uri(email)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    @Override
    public Collection<UserDTO> getAll(int pageNumber) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("page", pageNumber)
                        .build())
                .retrieve()
                .bodyToFlux(UserDTO.class)
                .collectList()
                .block();
    }


}
