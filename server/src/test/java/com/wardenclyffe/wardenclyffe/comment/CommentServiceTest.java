package com.wardenclyffe.wardenclyffe.comment;

import com.wardenclyffe.wardenclyffe.author.Author;
import com.wardenclyffe.wardenclyffe.author.AuthorRepository;
import com.wardenclyffe.wardenclyffe.post.Post;
import com.wardenclyffe.wardenclyffe.post.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class CommentServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void shouldReturnFindAll() {
        // Given
        final Long authorId = 1L;
        final Long postId = 1L;
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        author.setId(authorId);
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        post.setId(postId);
        List<Comment> commentList = new ArrayList();
        commentList.add(new Comment("My Comment", LocalDateTime.now(), LocalDateTime.now(), null, null));
        commentList.add(new Comment("Test Comment", LocalDateTime.now(), LocalDateTime.now(), null, null));
        Page<Comment> commentListPage = new PageImpl<>(commentList);
        Pageable pageable = PageRequest.of(0, 2);

        when(commentRepository.findAllByAuthorIdAndPostId(authorId, postId, pageable)).thenReturn(commentListPage);

        // When
        Page<Comment> expected = commentService.findAllByAuthorIdAndPostId(authorId, postId, pageable);

        // Then
        assertEquals(expected.getTotalElements(), commentListPage.getTotalElements());
    }

    @Test
    public void shouldReturnOneElement() {
        // Given
        Long postId = 2L;
        Long authorId = 1L;
        Long commentId = 3L;
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Comment comment = new Comment("Test Comment", LocalDateTime.now(), LocalDateTime.now(), null, null);
        given(commentRepository.findByAuthorIdAndPostIdAndId(authorId, postId, commentId)).willReturn(Optional.of(comment));

        // When
        final Comment expected = commentService.findById(authorId, postId, commentId);

        // Then
        assertThat(expected).isNotNull();
        assertEquals(expected, comment);
    }

    @Test
    public void shouldCreateNewComment() {
        // Given
        final Long authorId = 1L;
        final Long postId = 1L;
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Comment comment = new Comment("Test Comment", LocalDateTime.now(), LocalDateTime.now(), null, null);
        given(authorRepository.findById(authorId)).willReturn(Optional.of(author));
        given(postRepository.findByAuthorIdAndId(authorId, postId)).willReturn(Optional.of(post));
        given(commentRepository.save(comment)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // When
        Comment savedComment = commentService.create(authorId, postId, comment);

        // Then
        assertThat(savedComment).isNotNull();
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    public void shouldUpdateComment() {
        // Given
        final Long authorId = 1L;
        final Long postId = 1L;
        final Long commentId = 2L;
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Comment comment = new Comment("Test Comment", LocalDateTime.now(), LocalDateTime.now(), null, null);
        given(commentRepository.save(comment)).willReturn(comment);
        given(commentRepository.findByAuthorIdAndPostIdAndId(authorId, postId, commentId)).willReturn(Optional.of(comment));

        // When
        final Comment expected = commentService.update(authorId, postId, commentId, comment);

        // Then
        assertThat(expected).isNotNull();
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    public void shouldBeDeleted() {
        // Given
        final Long authorId = 1L;
        final Long postId = 1L;
        final Long commentId = 2L;

        // When
        commentService.delete(authorId, postId, commentId);

        // Then
        verify(commentRepository, atLeastOnce()).deleteByAuthorIdAndPostIdAndId(authorId, postId, commentId);
    }

}
