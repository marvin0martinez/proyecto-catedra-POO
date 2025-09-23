package com.example.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.users.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
}