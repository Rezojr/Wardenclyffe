package com.wardenclyffe.wardenclyffe.comment;

import com.wardenclyffe.wardenclyffe.AbstractIntegrationTest;
import com.wardenclyffe.wardenclyffe.author.Author;
import com.wardenclyffe.wardenclyffe.author.AuthorMapper;
import com.wardenclyffe.wardenclyffe.author.AuthorService;
import com.wardenclyffe.wardenclyffe.post.Post;
import com.wardenclyffe.wardenclyffe.post.PostDto;
import com.wardenclyffe.wardenclyffe.post.PostMapper;
import com.wardenclyffe.wardenclyffe.post.PostService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CommentControllerTest extends AbstractIntegrationTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void shouldFetchAllPosts() throws Exception {
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        final Comment comment = new Comment("My Comment", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);
        Post simulateExistingPost = postService.create(simulateExistingAuthor.getId(), post);
        Comment simulateExistingComment = commentService.create(simulateExistingAuthor.getId(), simulateExistingPost.getId(), comment);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/authors/{authorId}/posts/{postId}/comments", simulateExistingAuthor.getId(),simulateExistingPost.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty());
    }

    @Test
    public void shouldFetchCommentByAuthorIdAndPostId() throws Exception {
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        final Comment comment = new Comment("My Comment", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);
        Post simulateExistingPost = postService.create(simulateExistingAuthor.getId(), post);
        Comment simulateExistingComment = commentService.create(simulateExistingAuthor.getId(), simulateExistingPost.getId(), comment);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/authors/{authorId}/posts/{postId}/comments/{commentId}", simulateExistingAuthor.getId(),
                        simulateExistingPost.getId(), simulateExistingComment.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty());
    }

    @Test
    public void shouldCreateNewComment() throws Exception {
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        final CommentDto comment = new CommentDto("My Comment", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);
        Post simulateExistingPost = postService.create(simulateExistingAuthor.getId(), post);
        CommentDto simulateExistingComment = commentMapper.toDto(commentService.create(simulateExistingAuthor.getId(), simulateExistingPost.getId(), commentMapper.toEntity(comment)));


        mockMvc.perform(MockMvcRequestBuilders
                .post("/authors/{authorId}/posts/{postId}/comments", simulateExistingAuthor.getId(), simulateExistingPost.getId(), comment)
                .content(toJson(simulateExistingComment))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists());
    }

    @Test
    public void shouldDeleteComment() throws Exception {
        final Post post = new Post("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        final Comment comment = new Comment("My Comment", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);
        Post simulateExistingPost = postService.create(simulateExistingAuthor.getId(), post);
        Comment simulateExistingComment = commentService.create(simulateExistingAuthor.getId(), simulateExistingPost.getId(), comment);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/authors/{authorId}/posts/{postId}/comments/{commentId}", simulateExistingAuthor.getId(),
                        simulateExistingPost.getId(), simulateExistingComment.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateComment() throws Exception {
        final PostDto post = new PostDto("Title", "Content", LocalDateTime.now(), LocalDateTime.now(), 5, 5, null, null);
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        final CommentDto comment = new CommentDto("My Comment", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);
        Post simulateExistingPost = postService.create(simulateExistingAuthor.getId(), postMapper.toEntity(post));
        CommentDto simulateExistingComment = commentMapper.toDto(commentService.create(simulateExistingAuthor.getId(), simulateExistingPost.getId(), commentMapper.toEntity(comment)));

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/authors/{authorId}/posts/{postId}/comments/{commentId}", simulateExistingAuthor.getId(), simulateExistingPost.getId(), simulateExistingComment.getId(), simulateExistingComment)
                .content(toJson(new CommentDto("Test", LocalDateTime.now(), LocalDateTime.now(), null, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("Test"));
    }

}
