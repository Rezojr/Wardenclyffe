package com.wardenclyffe.wardenclyffe.comment;

import com.wardenclyffe.wardenclyffe.author.AuthorRepository;
import com.wardenclyffe.wardenclyffe.common.exception.NotFoundException;
import com.wardenclyffe.wardenclyffe.post.Post;
import com.wardenclyffe.wardenclyffe.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    public Page<Comment> findAllByAuthorIdAndPostId(Long authorId, Long postId, Pageable pageable) {
        return commentRepository.findAllByAuthorIdAndPostId(authorId, postId, pageable);
    }

    public Comment findById(Long authorId, Long postId, Long commentId) {
        return commentRepository.findByAuthorIdAndPostIdAndId(authorId, postId, commentId).orElseThrow(() -> new NotFoundException(postId, "Not found "));
    }

    @Transactional
    public Comment create(Long authorId, Long postId, Comment newComment) {
        newComment.setAuthor(authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException(authorId, "Author not found id ")));
        newComment.setPost(postRepository.findByAuthorIdAndId(authorId, postId).orElseThrow(() -> new NotFoundException(postId, "Post not found id ")));
        return commentRepository.save(newComment);
    }

    public Comment update(Long authorId, Long postId, Long commentId, Comment comment) {
        return commentRepository.findByAuthorIdAndPostIdAndId(authorId, postId, commentId).map(
                newComment -> {
                    newComment.setContent(comment.getContent());
                    return commentRepository.save(newComment);
                }).orElseThrow(() -> new NotFoundException(postId, "Cannot update post, not found id "));

    }

    public void delete(Long authorId, Long postId, Long commentId){
        commentRepository.deleteByAuthorIdAndPostIdAndId(authorId, postId, commentId);
    }

}
