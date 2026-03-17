package io.github.tgadek.libraapi.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Entity
@Table(name = "books")
@JsonInclude(NON_DEFAULT)
@JsonDeserialize(builder = Book.Builder.class)
public class Book {

    @Id
    @UuidGenerator
    @Column(name = "id", unique = true, updatable = false)
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "publishing_year")
    private String publishingYear;

    @Column(name = "status")
    private String status;

    @Column(name = "cover_url")
    private String coverUrl;

    public Book() {}

    private Book(String id, String title, String author, String isbn,
                String publisher, String publishingYear, String status, String coverUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publishingYear = publishingYear;
        this.status = status;
        this.coverUrl = coverUrl;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private String id;
        private String title;
        private String author;
        private String isbn;
        private String publisher;
        private String publishingYear;
        private String status;
        private String coverUrl;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public Builder publishingYear(String publishingYear) {
            this.publishingYear = publishingYear;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder coverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
            return this;
        }

        public Builder fromPrototype(Book book) {
            this.id = book.getId();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.isbn = book.getIsbn();
            this.publisher = book.getPublisher();
            this.publishingYear = book.getPublishingYear();
            this.status = book.getStatus();
            this.coverUrl = book.getCoverUrl();
            return this;
        }

        public Book build() {
            return new Book(id, title, author, isbn, publisher, publishingYear, status, coverUrl);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() { return id; }

    public String getTitle() { return title; }

    public String getAuthor() { return author; }

    public String getIsbn() { return isbn; }

    public String getPublisher() { return publisher; }

    public String getPublishingYear() { return publishingYear; }

    public String getStatus() { return status; }

    public String getCoverUrl() { return coverUrl; }
}
