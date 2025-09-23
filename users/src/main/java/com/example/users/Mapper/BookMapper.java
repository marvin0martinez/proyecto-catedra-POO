package com.example.users.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.users.dto.BookCreateDTO;
import com.example.users.dto.BookDTO;
import com.example.users.entity.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDTO toBookDTO(Book book);
    @Mapping(target = "id", ignore = true)
    Book toBook(BookCreateDTO bookCreateDTO);
}