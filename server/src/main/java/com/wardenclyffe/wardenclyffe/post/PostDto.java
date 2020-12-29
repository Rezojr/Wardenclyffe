package com.wardenclyffe.wardenclyffe.post;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import com.wardenclyffe.wardenclyffe.author.Author;
import com.wardenclyffe.wardenclyffe.common.IdDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto extends IdDto {

    @Length(min = 5, message = "Your title must have at least 5 characters")
    @NotEmpty(message = "Please provide title")
    private String title;
    private String content;
    private int likes;
    private int views;


    private Author author;


}
