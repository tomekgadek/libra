package io.github.tgadek.libraapi.service;

import io.github.tgadek.libraapi.domain.Book;
import io.github.tgadek.libraapi.repo.InMemoryBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    private BookServiceImpl bookService;
    private InMemoryBookRepository bookRepo;

    @BeforeEach
    void setUp() {
        bookRepo = new InMemoryBookRepository();
        bookService = new BookServiceImpl(bookRepo);
    }

    @Test
    void shouldCreateAndRetrieveBook() {
        // given
        Book book = Book.builder()
                .id("1")
                .title("TDD in Java")
                .author("Author")
                .build();

        // when
        bookService.createBook(book);
        Book found = bookService.getBook("1");

        // then
        assertEquals("TDD in Java", found.getTitle());
    }

    @Test
    void shouldGetAllBooksPaginated() {
        // given
        bookRepo.save(Book.builder().id("1").title("A").build());
        bookRepo.save(Book.builder().id("2").title("B").build());

        // when
        Page<Book> result = bookService.getAllBooks(0, 10);

        // then
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void shouldDeleteBook() {
        // given
        Book book = Book.builder().id("1").title("Delete Me").build();
        bookRepo.save(book);

        // when
        bookService.deleteBook(book);

        // then
        assertFalse(bookRepo.existsById("1"));
    }
}
