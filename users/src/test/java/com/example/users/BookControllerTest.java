package com.example.users;

import com.example.users.Controller.BookController; // Ajusta el paquete según tu estructura
import com.example.users.dto.BookCreateDTO;
import com.example.users.dto.BookDTO;
import com.example.users.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookControllerTest {

	@InjectMocks
	private BookController bookController;

	@Mock
	private BookService bookService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateBook() {
		// Preparar datos de prueba
		BookCreateDTO bookCreateDTO = new BookCreateDTO();
		bookCreateDTO.setTitle("Sample Book");
		bookCreateDTO.setAuthor("John Doe");
		bookCreateDTO.setPublicationDate(java.time.LocalDate.now());
		bookCreateDTO.setIsbn("1234567890123");
		bookCreateDTO.setPrice(29.99);

		BookDTO bookDTO = new BookDTO();
		bookDTO.setId(1L);
		bookDTO.setTitle("Sample Book");
		bookDTO.setAuthor("John Doe");
		bookDTO.setPublicationDate(java.time.LocalDate.now());
		bookDTO.setIsbn("1234567890123");
		bookDTO.setPrice(29.99);

		// Configurar el comportamiento del mock
		when(bookService.createBook(bookCreateDTO)).thenReturn(bookDTO);

		// Ejecutar el método del controlador
		ResponseEntity<BookDTO> response = bookController.createBook(bookCreateDTO);

		// Verificar resultados
		assertEquals(201, response.getStatusCodeValue());
		assertEquals(bookDTO, response.getBody());
		assertEquals(URI.create("/api/books/1"), response.getHeaders().getLocation());

		// Verificar que el método del servicio fue llamado
		verify(bookService, times(1)).createBook(bookCreateDTO);
	}
}
