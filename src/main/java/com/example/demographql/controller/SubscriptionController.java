package com.example.demographql.controller;

import com.example.demographql.model.Author;
import com.example.demographql.model.Book;
import com.example.demographql.publisher.AuthorPublisher;
import com.example.demographql.publisher.BookPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

/**
 * Controller xử lý các subscription GraphQL
 * Cho phép client lắng nghe các sự kiện real-time
 */
@Controller
public class SubscriptionController {

    private final BookPublisher bookPublisher;
    private final AuthorPublisher authorPublisher;

    @Autowired
    public SubscriptionController(BookPublisher bookPublisher, AuthorPublisher authorPublisher) {
        this.bookPublisher = bookPublisher;
        this.authorPublisher = authorPublisher;
    }

    /**
     * Subscription cho sự kiện tạo sách mới
     * @return Flux<Book> Luồng sự kiện sách mới
     */
    @SubscriptionMapping
    public Flux<Book> bookCreated() {
        return bookPublisher.getBookCreatedFlux();
    }

    /**
     * Subscription cho sự kiện cập nhật sách
     * @return Flux<Book> Luồng sự kiện sách được cập nhật
     */
    @SubscriptionMapping
    public Flux<Book> bookUpdated() {
        return bookPublisher.getBookUpdatedFlux();
    }

    /**
     * Subscription cho sự kiện tạo tác giả mới
     * @return Flux<Author> Luồng sự kiện tác giả mới
     */
    @SubscriptionMapping
    public Flux<Author> authorCreated() {
        return authorPublisher.getAuthorCreatedFlux();
    }

    /**
     * Subscription cho sự kiện cập nhật tác giả
     * @return Flux<Author> Luồng sự kiện tác giả được cập nhật
     */
    @SubscriptionMapping
    public Flux<Author> authorUpdated() {
        return authorPublisher.getAuthorUpdatedFlux();
    }
}
