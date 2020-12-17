package com.dekel.darwin.users.controller;

import com.dekel.darwin.users.domain.UserDTO;
import com.dekel.darwin.users.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

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
        userService.saveOrUpdate(user);
        return ResponseEntity.ok().build();
    }

    /**
     * @param email the user's email
     * @return the user if exists, otherwise not found response
     */
    @GetMapping("{email}")
    public ResponseEntity<?> getUser(@PathVariable String email) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    /**
     * @param email the user's email
     * @return ok response if a user has been deleted as a result. Not found response otherwise
     */
    @DeleteMapping("{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email) {
        userService.deleteByEmail(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "0") int pageNumber) {
        return ResponseEntity.ok(userService.getAll(pageNumber));
    }
}
