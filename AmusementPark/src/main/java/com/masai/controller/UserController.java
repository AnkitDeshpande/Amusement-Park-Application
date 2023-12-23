package com.masai.controller;

import com.masai.exception.SomethingWentWrongException;
import com.masai.exception.UserNotFoundException;
import com.masai.model.User;
import com.masai.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Integer userId) throws UserNotFoundException {
        User user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) throws SomethingWentWrongException {
        /* System.out.println("Inside create user controller."); */
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    /*
     * @GetMapping("/signin") public ResponseEntity<User>
     * logInUserHandler(Authentication auth) throws UserNotFoundException { User
     * custo = userService.findByEmail(auth.getName());
     *
     * return new ResponseEntity<>(custo, HttpStatus.ACCEPTED); }
     */

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@RequestBody User user, @PathVariable Integer userId)
            throws UserNotFoundException, SomethingWentWrongException {
        user.setUserId(userId);
        String message = userService.updateUser(user);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) throws UserNotFoundException {
        String message = userService.deleteUser(userId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/currentUser")
    public String getLoggedInUser(Principal principal) {
        return principal.getName();
    }
}
