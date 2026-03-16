package io.github.tgadek.libraapi.service;

import io.github.tgadek.libraapi.domain.Book;
import io.github.tgadek.libraapi.repo.BookRepo;
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
import java.util.function.BiFunction;
import java.util.function.Function;

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
        String coverUrl = photoFunction.apply(id, file);
        book.setCoverUrl(coverUrl);
        bookRepo.save(book);

        return coverUrl;
    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename)
            .filter(name -> name.contains("."))
            .map(name -> name.substring(name.lastIndexOf(".")))
            .orElse(".png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {

        String filename = id + fileExtension.apply(image.getOriginalFilename());

        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();

            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }

            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);

            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/books/image/" + filename)
                    .toUriString();

        } catch (Exception e) {
            throw new RuntimeException("Unable to save image");
        }
    };
}
