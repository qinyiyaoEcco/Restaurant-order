CREATE TABLE orders
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    is_delivery BOOLEAN NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP()
);

CREATE TABLE cart_items
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    menu_item_id BIGINT NOT NULL REFERENCES menu_items (id),
    quantity     INT    NOT NULL,
    order_id     BIGINT REFERENCES orders (id)
);
