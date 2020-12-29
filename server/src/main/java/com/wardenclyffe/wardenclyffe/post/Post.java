package com.wardenclyffe.wardenclyffe.post;

import com.sun.istack.NotNull;
import com.wardenclyffe.wardenclyffe.author.Author;
import com.wardenclyffe.wardenclyffe.common.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post extends IdEntity {

    @Length(min = 5, message = "Your title must have at least 5 characters")
    @NotEmpty(message = "Please provide title")
    private String title;
    private String content;
    private int likes;
    private int views;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull
    private Author author;

}
