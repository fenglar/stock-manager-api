create table product
(
    id        bigint auto_increment primary key,
    name      varchar(255)          not null,
    price     decimal(10,2)          not null
);



INSERT INTO product
    (id, name, price)
VALUES (1, 'Test Product', 10.00),
       (2, 'Test Product 2', 20.00);
