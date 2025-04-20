package com.example.demographql.service;

import com.example.demographql.dto.AuthorInput;
import com.example.demographql.exception.ValidationException;
import com.example.demographql.model.Author;
import com.example.demographql.publisher.AuthorPublisher;
import com.example.demographql.repository.AuthorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorPublisher authorPublisher;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorPublisher authorPublisher) {
        this.authorRepository = authorRepository;
        this.authorPublisher = authorPublisher;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public Author createAuthor(@Valid AuthorInput input) {
        // Kiểm tra tên tác giả không trống
        if (input.getName() == null || input.getName().trim().isEmpty()) {
            throw new ValidationException("Tên tác giả không được để trống");
        }
        
        Author author = Author.builder()
                .name(input.getName())
                .birthYear(input.getBirthYear())
                .build();
        
        Author savedAuthor = authorRepository.save(author);
        
        // Phát sự kiện tác giả mới được tạo
        authorPublisher.publishAuthorCreated(savedAuthor);
        
        return savedAuthor;
    }

    @Transactional
    public Author updateAuthor(Long id, @Valid AuthorInput input) {
        // Kiểm tra tác giả tồn tại
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Không tìm thấy tác giả với ID: " + id));
        
        // Kiểm tra tên tác giả không trống
        if (input.getName() == null || input.getName().trim().isEmpty()) {
            throw new ValidationException("Tên tác giả không được để trống");
        }
        
        author.setName(input.getName());
        author.setBirthYear(input.getBirthYear());
        
        Author updatedAuthor = authorRepository.save(author);
        
        // Phát sự kiện tác giả được cập nhật
        authorPublisher.publishAuthorUpdated(updatedAuthor);
        
        return updatedAuthor;
    }

    @Transactional
    public boolean deleteAuthor(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
