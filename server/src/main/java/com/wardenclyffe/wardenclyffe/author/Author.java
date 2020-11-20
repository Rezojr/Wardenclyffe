package com.wardenclyffe.wardenclyffe.author;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wardenclyffe.wardenclyffe.comment.Comment;
import com.wardenclyffe.wardenclyffe.common.IdEntity;
import com.wardenclyffe.wardenclyffe.common.exception.NotFoundException;
import com.wardenclyffe.wardenclyffe.post.Post;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authors")
public class Author extends IdEntity {

    private String firstName;
    private String lastName;

    @Email(message = "Provide a valid email")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    private String userName;
    @Length(min = 5, message = "XD")
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    private String profilePic;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonManagedReference(value = "author-post")
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @JsonManagedReference(value = "author-comment")
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();

    public Author(String firstName, String lastName, @Email(message = "Provide a valid email") @NotEmpty(message = "Email cannot be empty") String email, String userName, @Length(min = 5, message = "XD") @NotEmpty(message = "Password cannot be empty") String password, String profilePic, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.profilePic = profilePic;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
