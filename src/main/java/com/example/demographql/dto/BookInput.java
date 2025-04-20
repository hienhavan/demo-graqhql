package com.example.demographql.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class BookInput {
    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;
    
    @NotNull(message = "ID tác giả không được để trống")
    private Long authorId;
    
    @Min(value = 1, message = "Số trang phải lớn hơn 0")
    private Integer pageCount;
    
    private String genre;
    
    @Min(value = 1000, message = "Năm xuất bản không hợp lệ")
    @Max(value = 2100, message = "Năm xuất bản không hợp lệ")
    private Integer publishedYear;
}
