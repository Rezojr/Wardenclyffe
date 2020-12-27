package com.wardenclyffe.wardenclyffe.author;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wardenclyffe.wardenclyffe.comment.Comment;
import com.wardenclyffe.wardenclyffe.common.Constants;
import com.wardenclyffe.wardenclyffe.common.IdEntity;
import com.wardenclyffe.wardenclyffe.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "authors")
public class Author extends IdEntity {

    @NotNull
    @NotBlank
    @Size(max = Constants.NAME_MAX_CHARS)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(max = Constants.NAME_MAX_CHARS)
    private String lastName;

    @NotBlank
    @Email(message = "Provide a valid email")
    @Size(max = Constants.EMAIL_MAX_CHARS)
    private String email;

    private String phone;

    private String passwordHash;

    private String profilePic;
    private String description;

    @JsonManagedReference(value = "author-post")
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "author")
    private List<Post> posts = new ArrayList<>();

    @JsonManagedReference(value = "author-comment")
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "author")
    private List<Comment> comments = new ArrayList<>();
}
