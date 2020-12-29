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
@RequestMapping(value = "/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final PostMapper postMapper;

    @GetMapping
    public Page<AuthorDto> getAll(Pageable pageable) throws IOException {
        return authorMapper.mapAll(authorService.getAll(pageable));
    }

    @GetMapping(value = "/{id}")
    public AuthorDto findById(@PathVariable Long id) {
        return authorMapper.toDto(authorService.findById(id));
    }

    @PostMapping
    public AuthorDto create(@RequestBody Author author) {
        return authorMapper.toDto(authorService.create(author));
    }

    @PutMapping(value = "/{id}")
    public AuthorDto update(@PathVariable Long id,
                         @RequestBody Author author) {
        return authorMapper.toDto(authorService.update(id, author));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        authorService.delete(id);
    }

}
