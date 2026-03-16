package io.github.tgadek.libraapi.service;

import io.github.tgadek.libraapi.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {
    Page<Book> getAllBooks(int page, int size);
    Book getBook(String id);
    Book createBook(Book book);
    void deleteBook(Book book);
    String uploadCover(String id, MultipartFile file);
}
