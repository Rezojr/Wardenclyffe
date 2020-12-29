package com.wardenclyffe.wardenclyffe.author;

import com.wardenclyffe.wardenclyffe.common.exception.NotFoundException;
import com.wardenclyffe.wardenclyffe.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    @Value("classpath:images/logoo.png")
    Resource resource;

    public String convertImageToBase64String(Resource resource) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(resource.getFile());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public Page<Author> getAll(Pageable pageable) throws IOException {
        System.out.println(convertImageToBase64String(resource));
        return authorRepository.findAll(pageable);
    }

    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id, "Not found author id "));
    }

    public Author create(Author author) {
        return authorRepository.save(author);
    }

    public Author update(Long id, Author expectedAuthor) {
        return authorRepository.findById(id)
                .map(author -> {
                    author.setDescription(expectedAuthor.getDescription());
                    return authorRepository.save(author);
                }).orElseThrow(() -> new NotFoundException(id, "Cannot update author, not found id "));
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }


}
