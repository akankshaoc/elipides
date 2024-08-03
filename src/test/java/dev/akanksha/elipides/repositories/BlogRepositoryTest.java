package dev.akanksha.elipides.repositories;

import dev.akanksha.elipides.models.Blog;
import dev.akanksha.elipides.models.User;
import dev.akanksha.elipides.models.types.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.junit.jupiter.api.Assertions.*;

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

    Blog saveTestBlog() {
        Blog blog = Blog
                .builder()
                .title("Back to Earth")
                .user(user)
                .content("it's only a matter of time before you realise how mundane the reality is")
                .build();

        blogRepository.save(blog);
        return blog;
    }


    @Test
    void validateAddBlog() {
        Blog blog = saveTestBlog();
        blogRepository.flush();

        for(Blog b : userRepository.findById(user.getUsername()).get().getBlogs()) {
            if(b.getId() == blog.getId()) return;
        }

        throw new AssertionError("Blog Addition Failed");
    }

    @Test
    void validateBlogUpdate() {
        Blog testBlog = saveTestBlog();
        blogRepository.updateBlogTitle(testBlog.getId(), "This is your song");
        Assertions.assertThat(blogRepository.findById(testBlog.getId()).get().getTitle()).isEqualTo("This is your song");

        String verse1 = "It’s a little bit funny This feeling inside I’m not one of those who can easily hide I don’t have much money, but boy, if I did I’d buy a big house where we both could live";
        blogRepository.updateBlogTitle(testBlog.getId(), verse1);
        Assertions
                .assertThat(blogRepository.findById(testBlog.getId()).get().getTitle())
                .isEqualTo(verse1);
    }

    @Test
    void validateBlogUpvote() {
        Blog testBlog = saveTestBlog();
        long prev = testBlog.getUpVotes();
        testBlog.setUpVotes(prev + 1);
        Assertions.assertThat(blogRepository.findById(testBlog.getId()).get().getUpVotes()).isEqualTo(prev + 1);
    }

    @Test
    void validateBlogDeletion() {
        Blog testBlog = saveTestBlog();
        blogRepository.delete(testBlog);
        assertTrue(blogRepository.findById(testBlog.getId()).isEmpty());
    }
}