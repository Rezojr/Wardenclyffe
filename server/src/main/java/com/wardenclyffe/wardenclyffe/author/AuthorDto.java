package com.wardenclyffe.wardenclyffe.author;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wardenclyffe.wardenclyffe.comment.Comment;
import com.wardenclyffe.wardenclyffe.common.IdDto;
import com.wardenclyffe.wardenclyffe.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto extends IdDto {
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
}
