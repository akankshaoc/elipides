package dev.akanksha.elipides.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Comment {
    @Id @GeneratedValue
    private long id;
    private String content;

    @ManyToOne
    @JoinColumn (
            name = "blog_id",
            referencedColumnName = "id"
    )
    private Blog blog;

    @ManyToOne
    @JoinColumn (
            name = "username",
            referencedColumnName = "username"
    )
    private User user;
}
