package io.github.tgadek.libraapi.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class RootResource {

    @GetMapping
    public ResponseEntity<Map<String, Object>> index() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("application", "Libra API");
        response.put("version", "1.0.0");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("availableServices", List.of(
                Map.of(
                        "name", "Books",
                        "basePath", "/books",
                        "endpoints", List.of(
                                Map.of("method", "GET",    "path", "/books",              "description", "Get paginated list of books"),
                                Map.of("method", "GET",    "path", "/books/{id}",          "description", "Get a single book by ID"),
                                Map.of("method", "POST",   "path", "/books",              "description", "Create a new book"),
                                Map.of("method", "PUT",    "path", "/books/cover?id={id}", "description", "Upload cover image for a book"),
                                Map.of("method", "GET",    "path", "/books/image/{filename}", "description", "Get cover image by filename")
                        )
                )
        ));
        return ResponseEntity.ok(response);
    }
}
