package io.github.tgadek.libraapi.repo;

import io.github.tgadek.libraapi.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, String> {
    Optional<Book> findById(String id);
}
