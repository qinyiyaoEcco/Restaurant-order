CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);


INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$RhaYpKuQbyzuxtDypPKF6O2RivdZVaa9CzeCi91eRWikyvFwe/U8C', 'ADMIN');