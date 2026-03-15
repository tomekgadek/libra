package io.github.tgadek.libraapi.resource;

import io.github.tgadek.libraapi.domain.Book;
import io.github.tgadek.libraapi.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static io.github.tgadek.libraapi.constant.Constant.PHOTO_DIRECTORY;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/books")
public class BookResource {

    private final BookService bookService;

    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity
                .created(URI.create("/books/" + book.getId()))
                .body(bookService.createBook(book));
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getBooks(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(bookService.getAllBooks(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(bookService.getBook(id));
    }

    @PutMapping("/cover")
    public ResponseEntity<Map<String, String>> uploadCover(@RequestParam(value = "id") String id,
            @RequestParam(value = "file") MultipartFile file) {

        return ResponseEntity.ok().body(Map.of("coverUrl", bookService.uploadCover(id, file)));
    }

    @GetMapping(path = "/image/{filename}", produces = { IMAGE_PNG_VALUE, IMAGE_JPEG_VALUE })
    public byte[] getCover(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }
}
