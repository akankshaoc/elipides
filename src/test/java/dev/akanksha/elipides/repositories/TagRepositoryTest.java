package dev.akanksha.elipides.repositories;

import dev.akanksha.elipides.models.Blog;
import dev.akanksha.elipides.models.Tag;
import dev.akanksha.elipides.models.User;
import dev.akanksha.elipides.models.types.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.HashSet;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class TagRepositoryTest {
    TagRepository tagRepository;
    BlogRepository blogRepository;
    UserRepository userRepository;

    @Autowired
    TagRepositoryTest(TagRepository tagRepository, BlogRepository blogRepository, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    User user;

    @BeforeAll
    void setup() {
        user = User
                .builder()
                .username("oliviarodrigo")
                .email("olivia@test.com")
                .firstName("Olivia")
                .lastName("Rodrigo")
                .password("youcantcatchmenow")
                .role(UserRole.COMMON_USER)
                .build();
        userRepository.save(user);
    }

    @Test
    void validateRetrievalWithTag() {
        Blog blog = Blog.builder()
                .user(user)
                .title("Test Blog")
                .content("Test Content")
                .tags(new HashSet<>(Arrays.asList(
                        Tag.builder().value("test").build(),
                        Tag.builder().value("unit").build(),
                        Tag.builder().value("taylor").build()
                )))
                .build();

        blogRepository.saveAndFlush(blog);

        for(Blog b : blogRepository.findByTags_Value("taylor")) {
            if(b.getId() == blog.getId()) return;
        }

        throw new AssertionError("retrieval failed");
    }
}