package dev.akanksha.elipides.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Tag {
    @Id @GeneratedValue
    long id;
    String tag;

    @ManyToMany(mappedBy = "tags")
    Set<Blog> blogs;
}
