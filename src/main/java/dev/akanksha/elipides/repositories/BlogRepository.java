package dev.akanksha.elipides.repositories;

import dev.akanksha.elipides.models.Blog;
import dev.akanksha.elipides.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;


@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    @Query("UPDATE Blog b SET b.title = :title WHERE b.id = :id")
    @Modifying(clearAutomatically = true)
    void updateBlogTitle(@Param("id") long id, @Param("title") String title);

    @Query("UPDATE Blog b SET b.content = :content WHERE b.id = :id")
    @Modifying
    void updateBlogContent(@Param("id") long id, @Param("content") String content);

    @Query("UPDATE Blog b SET b.upVotes = b.upVotes + 1 WHERE b.id = :id")
    void upvoteBlog(@Param("id") long id);

    Set<Blog> findByTags_Value(String tag);
    Set<Blog> findByTags_ValueIn(Collection<Tag> tags);
}
