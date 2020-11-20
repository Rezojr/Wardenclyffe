package com.wardenclyffe.wardenclyffe.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authors/{authorId}/posts/{postId}")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comments")
    public Page<Comment> findAllComments(@PathVariable Long authorId,
                                         @PathVariable Long postId,
                                         Pageable pageable) {
        return commentService.findAllByAuthorIdAndPostId(authorId, postId, pageable);
    }

    @GetMapping("/comments/{commentId}")
    public Comment findById(@PathVariable Long authorId,
                            @PathVariable Long postId,
                            @PathVariable Long commentId) {
        return commentService.findById(authorId, postId, commentId);
    }

    @PostMapping("/comments")
    public Comment create(@PathVariable Long authorId,
                          @PathVariable Long postId,
                          @RequestBody Comment comment) {
        return commentService.create(authorId, postId, comment);
    }

    @PutMapping("/comments/{commentId}")
    public Comment update(@PathVariable Long authorId,
                          @PathVariable Long postId,
                          @PathVariable Long commentId,
                          @RequestBody Comment comment) {
        return commentService.update(authorId, postId, commentId, comment);
    }

    @DeleteMapping("/comments/{commentId}")
    public void delete(@PathVariable Long authorId,
                       @PathVariable Long postId,
                       @PathVariable Long commentId){
        commentService.delete(authorId, postId, commentId);
    }
}
