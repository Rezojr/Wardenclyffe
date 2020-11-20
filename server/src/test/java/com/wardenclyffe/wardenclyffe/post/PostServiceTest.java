package com.wardenclyffe.wardenclyffe.post;

import com.wardenclyffe.wardenclyffe.author.Author;
import com.wardenclyffe.wardenclyffe.author.AuthorRepository;
import com.wardenclyffe.wardenclyffe.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

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
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private PostService postService;


    @Test
    public void shouldReturnFindAll() {
        // Given
        final Long authorId = 1L;
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        author.setId(authorId);
        List<Post> postList = new ArrayList();
        postList.add(new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null));
        postList.add(new Post("Titleeee", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null));
        Page<Post> postListPage = new PageImpl<>(postList);
        Pageable pageable = PageRequest.of(0, 2);
        when(postRepository.findAllByAuthorId(authorId, pageable)).thenReturn(postListPage);

        // When
        Page<Post> expected = postService.findAllByAuthorId(Optional.of(author).get().getId(), pageable);

        // Then
        assertEquals(expected.getTotalElements(), postListPage.getTotalElements());
    }

    @Test
    public void shouldReturnOneElement() {
        // Given
        final Long postId = 1L;
        final Long authorId = 2L;
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        given(postRepository.findByAuthorIdAndId(authorId, postId)).willReturn(Optional.of(post));

        // When
        final Post expected = postService.findById(authorId, postId);

        // Then
        assertThat(expected).isNotNull();
        assertEquals(expected, post);
    }

    @Test
    public void shouldBeDeleted() {
        // Given
        final Long authorId = 1L;
        final Long postId = 1L;

        // When
        postService.delete(authorId, postId);

        // Then
        verify(postRepository, atLeastOnce()).deleteByAuthorIdAndId(authorId, postId);
    }

    @Test
    public void shouldUpdateAccount() {
        // Given
        final Long postId = 1L;
        final Long authorId = 1L;
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        given(postRepository.save(post)).willReturn(post);
        given(postRepository.findByAuthorIdAndId(authorId, postId)).willReturn(Optional.of(post));

        // When
        final Post expected = postService.update(authorId, postId, post);

        // Then
        assertThat(expected).isNotNull();
        verify(postRepository).save(any(Post.class));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionIfAccountNotFound() {
        postService.update(1L, 1L, new Post());
    }

    @Test
    public void shouldCreateNewAccount() {
        // Given
        Long authorId = 1L;
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        given(authorRepository.findById(authorId)).willReturn(Optional.of(author));
        given(postRepository.save(post)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // When
        Post savedPost = postService.create(authorId, post);

        // Then
        assertThat(savedPost).isNotNull();
        verify(postRepository).save(any(Post.class));
    }
    public Page<Post> getPagedPosts(int p, int s) {
        Pageable pageable = PageRequest.of(p, s, Sort.by("title"));
        return postRepository.findAll(pageable);

    }

    @Test
    public void shouldGetPagedPosts() {
        // Given
        final Long authorId = 1L;
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        author.setId(authorId);
        List<Post> postList = new ArrayList();
        postList.add(new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null));
        postList.add(new Post("Dragon Roar", "Grrrrrrr", LocalDateTime.now(), LocalDateTime.now(), 3, 8, null, null));
        postList.add(new Post("Please", "Test", LocalDateTime.now(), LocalDateTime.now(), 2, 1, null, null));
        postList.add(new Post("Me", "Me", LocalDateTime.now(), LocalDateTime.now(), 5, 7, null, null));
        postList.add(new Post("Test", "Please", LocalDateTime.now(), LocalDateTime.now(), 6, 7, null, null));
        Page<Post> postListPage = new PageImpl<>(postList);
        Pageable pageable = PageRequest.of(1, 2, Sort.by("title"));
        when(postRepository.findAllByAuthorId(authorId, pageable)).thenReturn(postListPage);

        // When
        Page<Post> expected = postService.findPagedPosts(authorId, 1, 2);

        // Then
        assertEquals(expected.getTotalElements(), postListPage.getTotalElements());
    }

}
