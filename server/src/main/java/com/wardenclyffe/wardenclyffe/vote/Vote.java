package com.wardenclyffe.wardenclyffe.vote;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wardenclyffe.wardenclyffe.author.Author;
import com.wardenclyffe.wardenclyffe.common.IdEntity;
import com.wardenclyffe.wardenclyffe.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "votes")
public class Vote extends IdEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Author author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", nullable = false)
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Post post;

    @CreationTimestamp
    @NotNull
    private LocalDate date;

}
