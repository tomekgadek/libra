package io.github.tgadek.libraapi.repo;

import io.github.tgadek.libraapi.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryBookRepository implements BookRepo {
    private final Map<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public Book save(Book book) {
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public Optional<Book> findById(String id) {
        return Optional.ofNullable(books.get(id));
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        List<Book> allBooks = new ArrayList<>(books.values());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), allBooks.size());
        return new PageImpl<>(allBooks.subList(start, end), pageable, allBooks.size());
    }

    @Override
    public void delete(Book book) {
        books.remove(book.getId());
    }

    @Override
    public long count() {
        return books.size();
    }

    @Override
    public boolean existsById(String id) {
        return books.containsKey(id);
    }
}
