package dev.akanksha.elipides.repositories;

import dev.akanksha.elipides.models.Blog;
import dev.akanksha.elipides.models.User;
import dev.akanksha.elipides.models.types.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest(showSql = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BlogRepositoryTest {

    private User user;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    @Autowired
    public BlogRepositoryTest(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    @BeforeAll
    void setTestUser() {
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
    void validateAddBlog() {
        Blog blog = Blog
                .builder()
                .title("Back to Earth")
                .user(user)
                .content("it's only a matter of time before you realise how mundane the reality is")
                .build();
//        user.setBlogs(new HashSet<>(Arrays.asList(blog)));
//        userRepository.save(user);
        blogRepository.save(blog);

        System.out.println(userRepository.findById("oliviarodrigo").get().getBlogs()); // []
        System.out.println(blogRepository.findAll()); // []
    }
}