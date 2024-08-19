package dev.akanksha.elipides.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.akanksha.elipides.exceptions.IntegrityException;
import dev.akanksha.elipides.exceptions.ValueException;
import dev.akanksha.elipides.models.User;
import dev.akanksha.elipides.models.types.UserRole;
import dev.akanksha.elipides.repositories.UserRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Validator validator;
    private final BCryptPasswordEncoder encoder;

    public List<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).stream().toList();
    }

    public User addUser(@Valid User user) throws IntegrityException {

        //1. Checks for Uniqueness
        if(userRepository.findById(user.getUsername()).isPresent())
            throw new IntegrityException("user with the username " + user.getUsername() + " already exists");

        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new IntegrityException("user with the email " + user.getEmail() + " already exists");

        //2. Checks for explicit admin registration
        if(user.getRole() != null
                && !user.getRole().toString().equalsIgnoreCase("COMMON_USER"))
            throw new IntegrityException("cannot register a user as ADMIN directly");

        user.setRole(UserRole.COMMON_USER);

        //3. user registration
        User savedUser = null;
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            savedUser = userRepository.save(user);
        } catch (Exception e) {
            throw new IntegrityException(e.getMessage());
        }
        return savedUser;
    }

    public void delete(String username) throws NoSuchElementException {
        User user = userRepository.findById(username).orElseThrow();
        userRepository.delete(user);
    }

    public User getUser(String username) throws NoSuchElementException {
        return userRepository.findById(username).orElseThrow();
    }

    public void updateUser(String username, @Valid User user) throws IntegrityException {
        User savedUser = this.getUser(username);
        if(user.getUsername() != null && !user.getUsername().equals(savedUser.getUsername())) {
            throw new IntegrityException("cannot update username");
        }
        user.setUsername(username);
        userRepository.save(user);
    }

    public void updateUserDetails(String username, Map<String, Object> updates) throws IntegrityException, ConstraintViolationException, ValueException {
        User user = this.getUser(username);
        for(String key : updates.keySet()) {
            switch (key) {
                case "username" -> throw new IntegrityException("username cannot be updated");
                case "firstName" -> user.setFirstName(updates.get(key).toString());
                case "lastName" -> user.setLastName(updates.get(key).toString());
                case "password" -> user.setPassword(updates.get(key).toString());
                case "email" -> user.setEmail(updates.get(key).toString());
                default -> throw new ValueException("This field is either non existent or not updatable");
            }
        }

        validator.validate(user);
        userRepository.save(user);
    }
}
