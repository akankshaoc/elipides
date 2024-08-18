package dev.akanksha.elipides.controllers;

import dev.akanksha.elipides.exceptions.IntegrityException;
import dev.akanksha.elipides.exceptions.ValueException;
import dev.akanksha.elipides.models.Message;
import dev.akanksha.elipides.models.User;
import dev.akanksha.elipides.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    ResponseEntity<List<User>> fetchAllUsers(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(name = "size", required = false, defaultValue = "20") int size) {
        return new ResponseEntity<>(userService.getAllUsers(page, size), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    ResponseEntity<User> getUser(@PathVariable("username") String username) throws NoSuchElementException {
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<User> addUser(@RequestBody User user) throws IntegrityException {
        User savedUser = userService.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    ResponseEntity<Message> updateUser(@PathVariable("username") String username, @RequestBody User user) throws IntegrityException {
        userService.updateUser(username, user);
        return new ResponseEntity<>(new Message("User updated"), HttpStatus.OK);
    }

    @PatchMapping("/{username}")
    ResponseEntity<Message> updateUserDetails(@PathVariable("username") String username, @RequestBody Map<String, Object> updates) throws IntegrityException, ValueException {
        userService.updateUserDetails(username, updates);
        return new ResponseEntity<>(new Message("User details updated"), HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    ResponseEntity<Message> deleteUser(@PathVariable("username") String username){
        userService.delete(username);
        return new ResponseEntity<>(new Message("User deleted"), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<List<String>> getOptionsOnCollection() {
        return new ResponseEntity<>(
                Arrays.asList("GET", "POST"),
                HttpStatus.OK
        );
    }

    @RequestMapping(path = "/{username}", method = RequestMethod.OPTIONS)
    ResponseEntity<List<String>> getOptionsOnResource() {
        return new ResponseEntity<>(
                Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"),
                HttpStatus.OK
        );
    }
}
