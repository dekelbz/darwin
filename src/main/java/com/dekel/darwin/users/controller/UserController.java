package com.dekel.darwin.users.controller;

import com.dekel.darwin.users.domain.UserDTO;
import com.dekel.darwin.users.presentationLayer.UserPresentationLayer;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserPresentationLayer userPresentationLayer;

    public UserController(UserPresentationLayer userPresentationLayer) {
        this.userPresentationLayer = userPresentationLayer;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> saveUser(@Valid @ModelAttribute UserDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return generateErrorResponse(bindingResult);
        }
        userPresentationLayer.saveOrUpdate(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<?> getUser(@RequestParam String email) {
        return userPresentationLayer.getByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        if (userPresentationLayer.deleteByEmail(email)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Collection<String>> generateErrorResponse(BindingResult bindingResult) {
        return ResponseEntity.badRequest()
                .body(bindingResult.getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()));
    }
}
