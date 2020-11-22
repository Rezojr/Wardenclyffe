package com.wardenclyffe.wardenclyffe.post;

import com.wardenclyffe.wardenclyffe.AbstractIntegrationTest;
import com.wardenclyffe.wardenclyffe.author.Author;
import com.wardenclyffe.wardenclyffe.author.AuthorDto;
import com.wardenclyffe.wardenclyffe.author.AuthorMapper;
import com.wardenclyffe.wardenclyffe.author.AuthorService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest extends AbstractIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private PostMapper postMapper;

    @Test
    public void shouldFetchAllPosts() throws Exception {
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);
        Post simulateExistingPost = postService.create(simulateExistingAuthor.getId(), post);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/authors/{authorId}/posts", simulateExistingAuthor.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].title").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].content").isNotEmpty());
    }

    @Test
    public void shouldFetchPostByAuthorId() throws Exception {
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);
        Post simulateExistingPost = postService.create(simulateExistingAuthor.getId(), post);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/authors/{authorId}/posts/{postId}", simulateExistingAuthor.getId(),simulateExistingPost.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty());
    }

    @Test
    public void shouldCreateNewPostForAuthor() throws Exception {
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);
        PostDto simulateExistingPost = postMapper.toDto(postService.create(simulateExistingAuthor.getId(), post));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/authors/{authorId}/posts", simulateExistingAuthor.getId())
                .content(toJson(simulateExistingPost))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").exists());
    }

    @Test
    public void shouldDeletePost() throws Exception {
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);
        Post simulateExistingPost = postService.create(simulateExistingAuthor.getId(), post);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/authors/{authorId}/posts/{postId}", simulateExistingAuthor.getId(), simulateExistingPost.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdatePost() throws Exception {
        final PostDto post = new PostDto("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);
        Post simulateExistingPost = postService.create(simulateExistingAuthor.getId(), postMapper.toEntity(post));

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/authors/{id}/posts/{postId}", simulateExistingAuthor.getId(), simulateExistingPost.getId(), simulateExistingPost)
                .content(toJson(new PostDto("Test", "XD", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("XD"));
    }

    @Test
    public void shouldFindPagedPosts() throws Exception {
        final Integer page = 0;
        final Integer size = 1;
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);
        Post simulateExistingPost = postService.create(simulateExistingAuthor.getId(), post);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/authors/{id}/posts/{page}/{size}", simulateExistingAuthor.getId(), page, size)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].title").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].content").isNotEmpty());
    }

}
