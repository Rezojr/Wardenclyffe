package com.wardenclyffe.wardenclyffe.author;

import com.wardenclyffe.wardenclyffe.post.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final PostMapper postMapper;

    @GetMapping("/authors")
    public Page<AuthorDto> getAll(Pageable pageable) throws IOException {
        return authorMapper.mapAll(authorService.getAll(pageable));
    }

    @GetMapping("/authors/{id}")
    public AuthorDto findById(@PathVariable Long id) {
        return authorMapper.toDto(authorService.findById(id));
    }

    @PostMapping("/authors")
    public AuthorDto create(@RequestBody Author author) {
        return authorMapper.toDto(authorService.create(author));
    }

    @PutMapping("/authors/{id}")
    public AuthorDto update(@PathVariable Long id,
                         @RequestBody Author author) {
        return authorMapper.toDto(authorService.update(id, author));
    }

    @DeleteMapping("/authors/{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }

}
