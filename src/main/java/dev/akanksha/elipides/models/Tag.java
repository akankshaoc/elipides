package dev.akanksha.elipides.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Builder
@Data
public class Tag {
    @Id @Column(name = "tag_value")
    String value;

    @ManyToMany(mappedBy = "tags")
    Set<Blog> blogs;
}
