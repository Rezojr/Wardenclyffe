package com.wardenclyffe.wardenclyffe.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByAuthorIdAndPostId(Long authorId, Long postId, Pageable pageable);
    Optional<Comment> findByAuthorIdAndPostIdAndId(Long authorId, Long postId, Long commentId);
    void deleteByAuthorIdAndPostIdAndId(Long authorId, Long postId, Long commentId);

}
