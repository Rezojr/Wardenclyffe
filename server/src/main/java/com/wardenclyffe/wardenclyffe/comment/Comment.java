package com.wardenclyffe.wardenclyffe.comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import com.wardenclyffe.wardenclyffe.author.Author;
import com.wardenclyffe.wardenclyffe.common.IdEntity;
import com.wardenclyffe.wardenclyffe.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends IdEntity {

    private String content;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonBackReference("author-comment")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "author_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_author_id"
            ))
    @ToString.Exclude
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Author author;

    @JsonBackReference(value = "post-comment")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "post_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_posts_id"
            ))
    @ToString.Exclude
    @NotNull
    private Post post;

}
