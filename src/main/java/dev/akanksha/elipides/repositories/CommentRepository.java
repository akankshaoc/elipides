package dev.akanksha.elipides.repositories;

import dev.akanksha.elipides.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("UPDATE Comment c SET c.content = :content WHERE c.id = :id")
    @Modifying(clearAutomatically = true)
    void updateComment(@Param("id") long id, @Param("content") String content);
}
