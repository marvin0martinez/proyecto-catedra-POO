package com.example.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.users.dto.BookCreateDTO;
import com.example.users.dto.BookDTO;
import com.example.users.entity.Book;
import com.example.users.exception.ResourceNotFoundException;
import com.example.users.Mapper.BookMapper;
import com.example.users.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookDTO createBook(BookCreateDTO bookCreateDTO) {
        if (bookRepository.existsByIsbn(bookCreateDTO.getIsbn())) {
            throw new IllegalArgumentException("ISBN already exists");
        }
        Book book = bookMapper.toBook(bookCreateDTO);
        return bookMapper.toBookDTO(bookRepository.save(book));
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return bookMapper.toBookDTO(book);
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toBookDTO)
                .collect(Collectors.toList());
    }

    public BookDTO updateBook(Long id, BookCreateDTO bookCreateDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        if (!book.getIsbn().equals(bookCreateDTO.getIsbn()) && bookRepository.existsByIsbn(bookCreateDTO.getIsbn())) {
            throw new IllegalArgumentException("ISBN already exists");
        }
        book.setTitle(bookCreateDTO.getTitle());
        book.setAuthor(bookCreateDTO.getAuthor());
        book.setPublicationDate(bookCreateDTO.getPublicationDate());
        book.setIsbn(bookCreateDTO.getIsbn());
        book.setPrice(bookCreateDTO.getPrice());
        return bookMapper.toBookDTO(bookRepository.save(book));
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
}
