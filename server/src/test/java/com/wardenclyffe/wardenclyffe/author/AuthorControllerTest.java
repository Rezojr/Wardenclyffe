package com.wardenclyffe.wardenclyffe.author;

import com.wardenclyffe.wardenclyffe.AbstractIntegrationTest;
import org.hibernate.validator.constraints.Length;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthorControllerTest extends AbstractIntegrationTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorMapper authorMapper;

    @Test
    public void shouldFetchAllAuthors() throws Exception {
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/authors")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].firstName").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].email").isNotEmpty());
    }

    @Test
    public void shouldFetchAuthorById() throws Exception {
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);

        this.mockMvc.perform(get("/authors/{id}", simulateExistingAuthor.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(author.getFirstName())))
                .andExpect(jsonPath("$.email", is(author.getEmail())));
    }

    @Test
    public void shouldCreateNewAuthor() throws Exception {
        final AuthorDto author = new AuthorDto("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        AuthorDto simulateExistingAuthor = authorMapper.toDto(authorService.create(authorMapper.toEntity(author)));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/authors")
                .content(toJson(simulateExistingAuthor))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").exists());
    }

    @Test
    public void shouldDeleteAuthor() throws Exception {
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        Author simulateExistingAuthor = authorService.create(author);

        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/authors/{id}", simulateExistingAuthor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateAuthor() throws Exception {
        final AuthorDto author = new AuthorDto("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        AuthorDto simulateExistingAuthor = authorMapper.toDto(authorService.create(authorMapper.toEntity(author)));

        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/authors/{id}", simulateExistingAuthor.getId())
                .content(toJson(new AuthorDto("Raven", "Drake", "krutons@gmail.com", "krypton", "xaaaass", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Raven"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Drake"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("Kss4rW"));
    }

}
