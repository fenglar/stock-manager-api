create table product
(
    id        bigint auto_increment primary key,
    name      varchar(255)          not null,
    price     decimal(10,2)          not null
);

CREATE TABLE stock (
    id                  bigint AUTO_INCREMENT PRIMARY KEY,
    current_quantity    bigint NOT NULL,
    product_id          bigint NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id)
);



INSERT INTO product
    (id, name, price)
VALUES (1, 'Test Product', 10.00),
       (2, 'Test Product 2', 20.00),
       (3, 'Test Product 3', 30.00),
       (4, 'Test Product 4', 30.00),
       (5, 'Test Product 5', 30.00),
       (6, 'Test Product 6', 30.00),
       (7, 'Test Product 7', 30.00),
       (8, 'Test Product 8', 30.00);

INSERT INTO stock
     (id, current_quantity, product_id)
VALUES
        (1, 10, 1),
        (2, 20, 2),
        (3, 5, 3);