package com.example.users.Mapper;

import com.example.users.dto.BookCreateDTO;
import com.example.users.dto.BookDTO;
import com.example.users.entity.Book;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-23T00:35:47-0600",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDTO toBookDTO(Book book) {
        if ( book == null ) {
            return null;
        }

        BookDTO bookDTO = new BookDTO();

        bookDTO.setId( book.getId() );
        bookDTO.setTitle( book.getTitle() );
        bookDTO.setAuthor( book.getAuthor() );
        bookDTO.setPublicationDate( book.getPublicationDate() );
        bookDTO.setIsbn( book.getIsbn() );
        bookDTO.setPrice( book.getPrice() );

        return bookDTO;
    }

    @Override
    public Book toBook(BookCreateDTO bookCreateDTO) {
        if ( bookCreateDTO == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        book.title( bookCreateDTO.getTitle() );
        book.author( bookCreateDTO.getAuthor() );
        book.publicationDate( bookCreateDTO.getPublicationDate() );
        book.isbn( bookCreateDTO.getIsbn() );
        book.price( bookCreateDTO.getPrice() );

        return book.build();
    }
}
