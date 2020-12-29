package com.wardenclyffe.wardenclyffe.author;

import com.wardenclyffe.wardenclyffe.common.Constants;
import com.wardenclyffe.wardenclyffe.common.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @Nullable
    private String phone;

    private String avatar;
    private String description;
}
