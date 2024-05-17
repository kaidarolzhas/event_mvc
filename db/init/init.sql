-- Создаем таблицу Person
CREATE TABLE IF NOT EXISTS person (
                                      id SERIAL PRIMARY KEY,
                                      firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    date_of_born DATE
    );

INSERT INTO person (firstname, lastname, username, password, role)
VALUES ('Admin', 'Adminovich', 'admin', '123', 'ROLE_ADMIN');
