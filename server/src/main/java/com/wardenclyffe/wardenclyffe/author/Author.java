package com.wardenclyffe.wardenclyffe.author;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wardenclyffe.wardenclyffe.common.Constants;
import com.wardenclyffe.wardenclyffe.common.IdEntity;
import com.wardenclyffe.wardenclyffe.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private String nickname;

    @NotBlank
    @Email(message = "Provide a valid email")
    @Size(max = Constants.EMAIL_MAX_CHARS)
    private String email;

    private String avatar;
    private String description;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "author")
    @JsonManagedReference(value = "author-post")
    private List<Post> posts = new ArrayList<>();
}
