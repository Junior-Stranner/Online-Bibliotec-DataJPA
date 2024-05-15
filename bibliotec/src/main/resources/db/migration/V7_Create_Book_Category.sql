CREATE TABLE IF NOT EXISTS Book (
    id  SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category_id BIGINT,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES Category(id)
);