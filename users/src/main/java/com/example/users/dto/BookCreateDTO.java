package com.example.users.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookCreateDTO {
    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    @NotBlank(message = "Author is mandatory")
    @Size(max = 50, message = "Author must not exceed 50 characters")
    private String author;

    @NotNull(message = "Publication date is mandatory")
    @PastOrPresent(message = "Publication date cannot be in the future")
    private LocalDate publicationDate;

    @NotBlank(message = "ISBN is mandatory")
    @Pattern(regexp = "\\d{13}", message = "ISBN must be a 13-digit number")
    private String isbn;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    @DecimalMax(value = "10000.00", message = "Price must not exceed 10000")
    private Double price;
}
