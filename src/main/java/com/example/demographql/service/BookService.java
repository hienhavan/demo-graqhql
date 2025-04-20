package com.example.demographql.service;

import com.example.demographql.dto.BookInput;
import com.example.demographql.exception.ValidationException;
import com.example.demographql.model.Author;
import com.example.demographql.model.Book;
import com.example.demographql.publisher.BookPublisher;
import com.example.demographql.repository.AuthorRepository;
import com.example.demographql.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookPublisher bookPublisher;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, BookPublisher bookPublisher) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookPublisher = bookPublisher;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre);
    }

    @Transactional
    public Book createBook(@Valid BookInput input) {
        // Kiểm tra tác giả tồn tại
        Author author = authorRepository.findById(input.getAuthorId())
                .orElseThrow(() -> new ValidationException("Không tìm thấy tác giả với ID: " + input.getAuthorId()));
        
        // Kiểm tra tiêu đề không trùng lặp
        if (input.getTitle() == null || input.getTitle().trim().isEmpty()) {
            throw new ValidationException("Tiêu đề sách không được để trống");
        }
        
        Book book = Book.builder()
                .title(input.getTitle())
                .author(author)
                .pageCount(input.getPageCount())
                .genre(input.getGenre())
                .publishedYear(input.getPublishedYear())
                .build();
        
        Book savedBook = bookRepository.save(book);
        
        // Phát sự kiện sách mới được tạo
        bookPublisher.publishBookCreated(savedBook);
        
        return savedBook;
    }

    @Transactional
    public Book updateBook(Long id, @Valid BookInput input) {
        // Kiểm tra sách tồn tại
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Không tìm thấy sách với ID: " + id));
        
        // Kiểm tra tác giả tồn tại
        Author author = authorRepository.findById(input.getAuthorId())
                .orElseThrow(() -> new ValidationException("Không tìm thấy tác giả với ID: " + input.getAuthorId()));
        
        // Kiểm tra tiêu đề không trùng lặp
        if (input.getTitle() == null || input.getTitle().trim().isEmpty()) {
            throw new ValidationException("Tiêu đề sách không được để trống");
        }
        
        book.setTitle(input.getTitle());
        book.setAuthor(author);
        book.setPageCount(input.getPageCount());
        book.setGenre(input.getGenre());
        book.setPublishedYear(input.getPublishedYear());
        
        Book updatedBook = bookRepository.save(book);
        
        // Phát sự kiện sách được cập nhật
        bookPublisher.publishBookUpdated(updatedBook);
        
        return updatedBook;
    }

    @Transactional
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
