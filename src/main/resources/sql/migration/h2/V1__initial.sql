CREATE TABLE IF NOT EXISTS BOOKS (
    id SERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    app BOOLEAN,
    owner VARCHAR(255),
    created DATE,
    modified DATE,
    PRIMARY KEY (id))