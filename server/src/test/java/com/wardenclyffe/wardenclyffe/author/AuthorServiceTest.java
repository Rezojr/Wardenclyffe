package com.wardenclyffe.wardenclyffe.author;

import com.wardenclyffe.wardenclyffe.common.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    public void shouldReturnFindAll() {
        // Given
        List<Author> bankList = new ArrayList();
        bankList.add(new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null));
        bankList.add(new Author("Rave", "Drake", "raven@gmail.com", "creed", "213gwa", "Picpic", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null));
        Page<Author> authorListPage = new PageImpl<>(bankList);
        Pageable pageable = PageRequest.of(0, 2);
        when(authorRepository.findAll(pageable)).thenReturn(authorListPage);

        // When
        Page<Author> expected = authorService.getAll(pageable);

        // Then
        assertEquals(expected.getTotalElements(), authorListPage.getTotalElements());
    }

    @Test
    public void findBankById() {
        // Given
        final Long id = 1L;
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        given(authorRepository.findById(id)).willReturn(Optional.of(author));

        // When
        final Author expected = authorService.findById(id);

        // Then
        assertThat(expected).isNotNull();
        assertEquals(expected, author);
    }

    @Test
    public void shouldBeDeleted() {
        // Given
        final Long id = 1L;

        // When
        authorService.delete(id);

        // Then
        verify(authorRepository, atLeastOnce()).deleteById(id);
    }

    @Test
    public void updateBank() {
        // Given
        final Long id = 1L;
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        given(authorRepository.save(author)).willReturn(author);
        given(authorRepository.findById(id)).willReturn(Optional.of(author));

        // When
        final Author expected = authorService.update(id, author);

        // Then
        assertThat(expected).isNotNull();
        verify(authorRepository).save(any(Author.class));
    }

    @Test(expected = NotFoundException.class)
    public void shouldThrowExceptionIfNotFound() {
        Mockito.when(authorRepository.findById(anyLong())).thenThrow(new NotFoundException(1L, "XD"));
        authorService.update(1L, new Author());
    }

    @Test
    public void shouldCreateNewBank() {
        // Given
        final Author author = new Author("Kruton", "Strange", "krutons@gmail.com", "krypton", "Kss4rW", "Test", "Description", LocalDateTime.now(), LocalDateTime.now(), null, null);
        given(authorRepository.save(author)).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // When
        Author expected = authorService.create(author);

        // Then
        assertThat(expected).isNotNull();
        verify(authorRepository).save(any(Author.class));
    }


}
