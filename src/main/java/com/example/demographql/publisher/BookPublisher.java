package com.example.demographql.publisher;

import com.example.demographql.model.Book;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Publisher cho các sự kiện liên quan đến Book để sử dụng trong GraphQL Subscriptions
 */
@Component
public class BookPublisher {
    
    // Sink cho sự kiện tạo sách mới
    private final Sinks.Many<Book> bookCreatedSink = Sinks.many().multicast().onBackpressureBuffer();
    
    // Sink cho sự kiện cập nhật sách
    private final Sinks.Many<Book> bookUpdatedSink = Sinks.many().multicast().onBackpressureBuffer();
    
    /**
     * Phát sự kiện khi sách mới được tạo
     * @param book Sách mới được tạo
     */
    public void publishBookCreated(Book book) {
        bookCreatedSink.tryEmitNext(book);
    }
    
    /**
     * Phát sự kiện khi sách được cập nhật
     * @param book Sách được cập nhật
     */
    public void publishBookUpdated(Book book) {
        bookUpdatedSink.tryEmitNext(book);
    }
    
    /**
     * Lấy luồng sự kiện sách mới được tạo
     * @return Flux<Book> Luồng sự kiện sách mới
     */
    public Flux<Book> getBookCreatedFlux() {
        return bookCreatedSink.asFlux();
    }
    
    /**
     * Lấy luồng sự kiện sách được cập nhật
     * @return Flux<Book> Luồng sự kiện sách được cập nhật
     */
    public Flux<Book> getBookUpdatedFlux() {
        return bookUpdatedSink.asFlux();
    }
}
