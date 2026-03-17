package io.github.tgadek.libraapi.service;

import io.github.tgadek.libraapi.domain.Book;
import io.github.tgadek.libraapi.repo.BookRepo;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static io.github.tgadek.libraapi.constant.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Transactional(rollbackOn = Exception.class)
public class BookServiceImpl implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepo bookRepo;

    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @PostConstruct
    public void seedData() {
        if (bookRepo.count() == 0) {
            log.info("Seeding initial book data using Builder");
            bookRepo.save(Book.builder()
                    .title("The Clean Coder")
                    .author("Robert C. Martin")
                    .isbn("978-0137081073")
                    .publisher("Prentice Hall")
                    .publishingYear("2011")
                    .status("Available")
                    .build());
            bookRepo.save(Book.builder()
                    .title("Clean Architecture")
                    .author("Robert C. Martin")
                    .isbn("978-0134494166")
                    .publisher("Prentice Hall")
                    .publishingYear("2017")
                    .status("Available")
                    .build());
        }
    }

    @Override
    public Page<Book> getAllBooks(int page, int size) {
        return bookRepo.findAll(PageRequest.of(page, size, Sort.by("title")));
    }

    @Override
    public Book getBook(String id) {
        return bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    @Override
    public Book createBook(Book book) {
        return bookRepo.save(book);
    }

    @Override
    public void deleteBook(Book book) {
        bookRepo.delete(book);
    }

    @Override
    public String uploadCover(String id, MultipartFile file) {
        log.info("Saving picture for book ID: {}", id);

        Book book = getBook(id);
        String coverUrl = savePhoto(id, file);
        Book updatedBook = Book.builder()
                .fromPrototype(book)
                .coverUrl(coverUrl)
                .build();
        bookRepo.save(updatedBook);

        return coverUrl;
    }

    private String getFileExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(name -> name.contains("."))
                .map(name -> name.substring(name.lastIndexOf(".")))
                .orElse(".png");
    }

    private String savePhoto(String id, MultipartFile file) {
        String filename = id + getFileExtension(file.getOriginalFilename());

        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();

            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }

            Files.copy(file.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);

            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/books/image/" + filename)
                    .toUriString();

        } catch (Exception e) {
            log.error("Unable to save image for book ID: {}", id, e);
            throw new RuntimeException("Unable to save image");
        }
    }
}
