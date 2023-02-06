
create table product
(
    id       bigint auto_increment
        primary key,
    name     varchar(255) not null,
    quantity bigint       not null
);



INSERT INTO product
    (id, name, quantity)
VALUES (1, 'Test Product', 10),
       (2, 'Test Product 2', 20);
