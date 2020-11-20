package com.wardenclyffe.wardenclyffe.author;

import com.wardenclyffe.wardenclyffe.common.exception.NotFoundException;
import com.wardenclyffe.wardenclyffe.post.Post;
import com.wardenclyffe.wardenclyffe.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;

    public Page<Author> getAll(Pageable pageable){
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
                    author.setFirstName(expectedAuthor.getFirstName());
                    author.setLastName(expectedAuthor.getLastName());
                    author.setDescription(expectedAuthor.getDescription());
                    author.setProfilePic(expectedAuthor.getProfilePic());
                    return authorRepository.save(author);
                }).orElseThrow(() -> new NotFoundException(id, "Cannot update author, not found id "));
    }

    public void delete(Long id) {
        authorRepository.deleteById(id);
    }


}
