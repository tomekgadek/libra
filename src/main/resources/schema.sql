CREATE TABLE IF NOT EXISTS books (
    id VARCHAR(36) PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    isbn VARCHAR(50),
    publisher VARCHAR(255),
    publishing_year VARCHAR(4),
    status VARCHAR(50),
    cover_url VARCHAR(255)
);
