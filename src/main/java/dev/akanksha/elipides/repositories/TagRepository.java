package dev.akanksha.elipides.repositories;

import dev.akanksha.elipides.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByValue(String value);
    Optional<Tag> findByValueStartingWith(String prefix);
}
