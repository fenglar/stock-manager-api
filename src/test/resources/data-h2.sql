create table product
(
    id        bigint auto_increment primary key,
    name      varchar(255)          not null,
    price     decimal(10,2)          not null
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
       (8, 'Test Product 8', 30.00)
       ;
