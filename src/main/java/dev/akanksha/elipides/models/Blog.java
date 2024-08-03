package dev.akanksha.elipides.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Blog {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    private long upVotes;
    @ManyToOne
    @JoinColumn(
            name = "username",
            referencedColumnName = "username",
            nullable = false
    )
    private User user;

    private LocalDateTime firstCreated;
    private LocalDateTime lastUpdates;

    @ManyToMany(
            cascade = CascadeType.ALL
    )
    @JoinTable(
            name = "blog_tag",
            joinColumns = @JoinColumn( name = "blog_id"),
            inverseJoinColumns = @JoinColumn( name = "tag_value")
    )
    Set<Tag> tags;

    @OneToMany(mappedBy = "blog")
    Set<Comment> comments;
}
