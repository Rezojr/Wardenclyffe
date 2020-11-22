package com.wardenclyffe.wardenclyffe.post;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors/{authorId}")
public class PostController {

    @Autowired
    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping("/posts")
    public Page<Post> findAllByAuthorId(@PathVariable Long authorId,
                                        @PageableDefault Pageable pageable) {
        return postService.findAllByAuthorId(authorId, pageable);
    }

    @GetMapping("/posts/{page}/{size}")
    public Page<Post> getPagedPosts(@PathVariable Long authorId,
                                    @PathVariable Integer page,
                                    @PathVariable Integer size) {
        return postService.findPagedPosts(authorId, page, size);
    }

    @GetMapping("/posts/{postId}")
    public Post findById(@PathVariable Long authorId,
                         @PathVariable Long postId) {
        return postService.findById(authorId, postId);
    }

    @PostMapping("/posts")
    public Post create(@PathVariable Long authorId,
                       @RequestBody Post post) {
        return postService.create(authorId, post);
    }

    @PutMapping("/posts/{postId}")
    public Post update(@PathVariable Long authorId,
                       @PathVariable Long postId,
                       @RequestBody Post post) {
        return postService.update(authorId, postId, post);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long authorId,
                       @PathVariable Long postId) {
        postService.delete(authorId, postId);
    }

}
