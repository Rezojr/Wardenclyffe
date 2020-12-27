package com.wardenclyffe.wardenclyffe.comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import com.wardenclyffe.wardenclyffe.author.Author;
import com.wardenclyffe.wardenclyffe.common.IdDto;
import com.wardenclyffe.wardenclyffe.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto extends IdDto {
    private String content;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "author_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_authors_id"
            ))
    @ToString.Exclude
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Author author;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "post_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_posts_id"
            ))
    @ToString.Exclude
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Post post;
}
