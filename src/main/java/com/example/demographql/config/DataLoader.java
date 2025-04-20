package com.example.demographql.config;

import com.example.demographql.model.Author;
import com.example.demographql.model.Book;
import com.example.demographql.repository.AuthorRepository;
import com.example.demographql.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public DataLoader(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        // Tạo các tác giả
        Author nguyenNhatAnh = Author.builder()
                .name("Nguyễn Nhật Ánh")
                .birthYear(1955)
                .build();
        
        Author toHoai = Author.builder()
                .name("Tô Hoài")
                .birthYear(1920)
                .build();
        
        Author vietThanh = Author.builder()
                .name("Nguyễn Việt Thành")
                .birthYear(1970)
                .build();
        
        // Lưu tác giả vào database
        authorRepository.save(nguyenNhatAnh);
        authorRepository.save(toHoai);
        authorRepository.save(vietThanh);
        
        // Tạo các sách
        Book matBiec = Book.builder()
                .title("Mắt biếc")
                .author(nguyenNhatAnh)
                .pageCount(300)
                .genre("Tiểu thuyết")
                .publishedYear(1990)
                .build();
        
        Book toiThayHoaVangTrenCoXanh = Book.builder()
                .title("Tôi thấy hoa vàng trên cỏ xanh")
                .author(nguyenNhatAnh)
                .pageCount(250)
                .genre("Tiểu thuyết")
                .publishedYear(2010)
                .build();
        
        Book deTuiNoiChoMaNghe = Book.builder()
                .title("Để tui nói cho mà nghe")
                .author(vietThanh)
                .pageCount(200)
                .genre("Tùy bút")
                .publishedYear(2015)
                .build();
        
        Book deTMenPhieuLuuKy = Book.builder()
                .title("Dế Mèn phiêu lưu ký")
                .author(toHoai)
                .pageCount(150)
                .genre("Thiếu nhi")
                .publishedYear(1941)
                .build();
        
        // Lưu sách vào database
        bookRepository.save(matBiec);
        bookRepository.save(toiThayHoaVangTrenCoXanh);
        bookRepository.save(deTuiNoiChoMaNghe);
        bookRepository.save(deTMenPhieuLuuKy);
    }
}
