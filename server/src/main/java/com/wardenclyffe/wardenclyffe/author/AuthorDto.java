package com.wardenclyffe.wardenclyffe.author;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wardenclyffe.wardenclyffe.common.IdDto;
import com.wardenclyffe.wardenclyffe.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto extends IdDto {
    private String nickname;

    @Email(message = "Provide a valid email")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    private String avatar;
    private String description;

    @JsonManagedReference(value = "author-post")
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "author")
    private List<Post> posts = new ArrayList<>();
}
