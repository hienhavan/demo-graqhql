package com.example.demographql.controller;

import com.example.demographql.dto.BookInput;
import com.example.demographql.model.Book;
import com.example.demographql.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Query Resolvers - Các phương thức xử lý truy vấn

    @QueryMapping
    public List<Book> allBooks() {
        return bookService.getAllBooks();
    }

    @QueryMapping
    public Book bookById(@Argument Long id) {
        return bookService.getBookById(id).orElse(null);
    }

    @QueryMapping
    public List<Book> booksByGenre(@Argument String genre) {
        return bookService.getBooksByGenre(genre);
    }

    // Mutation Resolvers - Các phương thức xử lý thay đổi dữ liệu

    @MutationMapping
    public Book createBook(@Argument BookInput input) {
        return bookService.createBook(input);
    }

    @MutationMapping
    public Book updateBook(@Argument Long id, @Argument BookInput input) {
        return bookService.updateBook(id, input);
    }

    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        return bookService.deleteBook(id);
    }
}
