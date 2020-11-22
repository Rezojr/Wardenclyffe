package com.wardenclyffe.wardenclyffe.post;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByAuthorId(Long authorId, Pageable pageable);
    Optional<Post> findByAuthorIdAndId(Long authorId, Long postId);
    void deleteByAuthorIdAndId(Long authorId, Long postId);
}