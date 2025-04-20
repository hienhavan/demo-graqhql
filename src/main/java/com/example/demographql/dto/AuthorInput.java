package com.example.demographql.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class AuthorInput {
    @NotBlank(message = "Tên tác giả không được để trống")
    private String name;
    
    @Min(value = 1000, message = "Năm sinh không hợp lệ")
    @Max(value = 2100, message = "Năm sinh không hợp lệ")
    private Integer birthYear;
}
