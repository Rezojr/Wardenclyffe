package com.wardenclyffe.wardenclyffe.post;

import com.wardenclyffe.wardenclyffe.author.AuthorRepository;
import com.wardenclyffe.wardenclyffe.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final AuthorRepository authorRepository;


    public Page<Post> findAllByAuthorId(Long authorId, Pageable pageable) {
        return postRepository.findAllByAuthorId(authorId, pageable);
    }

    public Post findById(Long authorId, Long postId) {
        return postRepository.findByAuthorIdAndId(authorId, postId).orElseThrow(() -> new NotFoundException(postId, "Not found "));
    }

    public Page<Post> findPagedPosts(Long authorId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title"));
        return postRepository.findAllByAuthorId(authorId, pageable);

    }

    public Post create(Long authorId, Post post) {
        post.setAuthor(authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException(authorId, "Not found author id ")));
        return postRepository.save(post);
    }

    public Post update(Long authorId, Long postId, Post post) {
        return postRepository.findByAuthorIdAndId(authorId, postId).map(
                newPost -> {
                    newPost.setTitle(post.getTitle());
                    newPost.setContent(post.getContent());
                    return postRepository.save(newPost);
                }).orElseThrow(() -> new NotFoundException(postId, "Cannot update post, not found id "));

    }

    public void delete(Long authorId, Long postId) {
        postRepository.deleteByAuthorIdAndId(authorId, postId);
    }


}
