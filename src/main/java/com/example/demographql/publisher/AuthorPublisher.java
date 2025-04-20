package com.example.demographql.publisher;

import com.example.demographql.model.Author;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * Publisher cho các sự kiện liên quan đến Author để sử dụng trong GraphQL Subscriptions
 */
@Component
public class AuthorPublisher {
    
    // Sink cho sự kiện tạo tác giả mới
    private final Sinks.Many<Author> authorCreatedSink = Sinks.many().multicast().onBackpressureBuffer();
    
    // Sink cho sự kiện cập nhật tác giả
    private final Sinks.Many<Author> authorUpdatedSink = Sinks.many().multicast().onBackpressureBuffer();
    
    /**
     * Phát sự kiện khi tác giả mới được tạo
     * @param author Tác giả mới được tạo
     */
    public void publishAuthorCreated(Author author) {
        authorCreatedSink.tryEmitNext(author);
    }
    
    /**
     * Phát sự kiện khi tác giả được cập nhật
     * @param author Tác giả được cập nhật
     */
    public void publishAuthorUpdated(Author author) {
        authorUpdatedSink.tryEmitNext(author);
    }
    
    /**
     * Lấy luồng sự kiện tác giả mới được tạo
     * @return Flux<Author> Luồng sự kiện tác giả mới
     */
    public Flux<Author> getAuthorCreatedFlux() {
        return authorCreatedSink.asFlux();
    }
    
    /**
     * Lấy luồng sự kiện tác giả được cập nhật
     * @return Flux<Author> Luồng sự kiện tác giả được cập nhật
     */
    public Flux<Author> getAuthorUpdatedFlux() {
        return authorUpdatedSink.asFlux();
    }
}
