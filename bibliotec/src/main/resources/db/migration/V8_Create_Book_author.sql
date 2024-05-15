CREATE TABLE IF NOT EXISTS book_author (
    book_id BIGINT,
    author_id BIGINT,
    CONSTRAINT fk_book FOREIGN KEY (book_id) REFERENCES Book(id),
    CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES Author(id)
);