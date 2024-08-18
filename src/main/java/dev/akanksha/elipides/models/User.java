package dev.akanksha.elipides.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.akanksha.elipides.models.types.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "application_user")
public class User {
    @Id
    String username;

    @Column(nullable = false)
    @NotBlank
    @Length(max = 40, message = "firstName cannot be longer than 40 characters")
    String firstName;

    @Length(max = 40, message = "lastName cannot be longer than 40 characters")
    String lastName;

    @Length(max = 50, message = "status cannot exceed 50 characters")
    String status;

    @Column(nullable = false)
    @Length(min = 8, message = "password must be atleast 8 characters long")
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false, nullable = false)
    UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<Blog> blogs;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    String email;

    public void addBlog(Blog blog) {
        if(this.blogs == null) blogs = new HashSet<>();
        this.blogs.add(blog);
    }
}
