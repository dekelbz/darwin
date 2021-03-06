package com.dekel.darwin.users.controller;

import com.dekel.darwin.users.domain.UserDTO;
import com.dekel.darwin.users.service.UserService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Save a given user with its properties in the DB. If the user exists already,
     * update his properties.
     * @param user the user object to be saved. {@link UserDTO#getEmail} will be used to try to fetch
     *             the user from the DB, if exists already
     * @return ok if the user is created. Bad request response if not all user properties are valid
     */
    @PostMapping
    public ResponseEntity<?> saveOrUpdateUser(@Valid UserDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(generateErrorResponse(bindingResult));
        }
        userService.saveOrUpdate(user);
        return ResponseEntity.ok().build();
    }

    /**
     * @param email the user's email
     * @return the user if exists, otherwise not found response
     */
    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam String email) throws ExecutionException, InterruptedException {
        return userService.getByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    /**
     * @param email the user's email
     * @return ok response if a user has been deleted as a result. Not found response otherwise
     */
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        userService.deleteByEmail(email);
        return ResponseEntity.ok().build();
    }

    private Collection<String> generateErrorResponse(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
