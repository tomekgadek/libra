package io.github.tgadek.libraapi.repo;

import io.github.tgadek.libraapi.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface BookRepo extends org.springframework.data.repository.Repository<Book, String> {
    Page<Book> findAll(Pageable pageable);

    Optional<Book> findById(String id);

    Book save(Book book);

    void delete(Book book);

    long count();

    boolean existsById(String id);
}
