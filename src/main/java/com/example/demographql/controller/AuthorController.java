package com.example.demographql.controller;

import com.example.demographql.dto.AuthorInput;
import com.example.demographql.model.Author;
import com.example.demographql.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Query Resolvers - Các phương thức xử lý truy vấn

    @QueryMapping
    public List<Author> allAuthors() {
        return authorService.getAllAuthors();
    }

    @QueryMapping
    public Author authorById(@Argument Long id) {
        return authorService.getAuthorById(id).orElse(null);
    }

    // Mutation Resolvers - Các phương thức xử lý thay đổi dữ liệu

    @MutationMapping
    public Author createAuthor(@Argument AuthorInput input) {
        return authorService.createAuthor(input);
    }

    @MutationMapping
    public Author updateAuthor(@Argument Long id, @Argument AuthorInput input) {
        return authorService.updateAuthor(id, input);
    }

    @MutationMapping
    public Boolean deleteAuthor(@Argument Long id) {
        return authorService.deleteAuthor(id);
    }
}
