package dev.akanksha.elipides.models;

import dev.akanksha.elipides.models.types.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "application_user")
public class User {
    @Id
    String username;

    @Column(nullable = false)
    String firstName;
    String lastName;
    String status;

    @Column(nullable = false)
    String password;

    @Enumerated(EnumType.STRING)@Column(updatable = false, nullable = false)
    UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<Blog> blogs;

    @Column(nullable = false)
    String email;

    public void addBlog(Blog blog) {
        this.blogs.add(blog);
    }
}
