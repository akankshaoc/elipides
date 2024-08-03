package dev.akanksha.elipides.repositories;

import dev.akanksha.elipides.models.User;
import dev.akanksha.elipides.models.types.UserRole;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    UserRepository userRepository;

    @Autowired
    UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void validateAddNewUser() {
        User user = User.builder()
                .username("testgod")
                .firstName("Test")
                .lastName("God")
                .email("god@test.com")
                .password("password")
                .role(UserRole.COMMON_USER)
                .build();

        userRepository.save(user);

        User testGod = userRepository.findById("testgod").orElseThrow(InternalError::new);
        assertEquals("god@test.com", testGod.getEmail());
    }

    @Test @Transactional
    void validateUpdateUserProperties() {
        User user = User.builder()
                .username("testgod")
                .firstName("Test")
                .lastName("God")
                .email("god@test.com")
                .password("password")
                .role(UserRole.COMMON_USER)
                .build();

        userRepository.save(user);

        userRepository.updateEmail("goddess@test.com", "testgod");
        userRepository.updateStatus("God is a woman", "testgod");
        userRepository.updateFirstName("Testess", "testgod");
        userRepository.updateLastName("Goddess", "testgod");
        userRepository.updatePassword("aintitfunny", "testgod");
        User testGod = userRepository.findById("testgod").orElseThrow(InternalError::new);

        assertEquals("goddess@test.com", testGod.getEmail());
        assertEquals("Testess", testGod.getFirstName());
        assertEquals("Goddess", testGod.getLastName());
        assertEquals("God is a woman", testGod.getStatus());
        assertEquals("aintitfunny", testGod.getPassword());
    }
}