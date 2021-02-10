create table product
(
    id         bigserial primary key,
    title     varchar(255),
    cost      int,
    create_at timestamp default current_timestamp,
    update_at timestamp default current_timestamp
);

create table users (
  id        bigserial primary key,
  username  varchar(30) not null unique,
  password  varchar(80) not null,
  email     varchar(50) unique,
  created_at    timestamp default current_timestamp,
  updated_at    timestamp default current_timestamp
);

create table orders(
                       id              bigserial primary key,
                       id_user         bigint not null references users (id),
                       id_product      bigint not null references product (id),
                       order_number    bigint,
                       count           int,
                       sum_cost        int,
                       adres           varchar(30),
                       create_at timestamp default current_timestamp
);

create table roles(
    id      bigserial primary key,
    name    varchar(50) not null unique,
    created_at  timestamp default current_timestamp,
    updated_at    timestamp default current_timestamp
 );
create table users_roles(
  user_id   bigint not null references users(id),
  role_id   bigint not null references roles(id),
  primary key (user_id, role_id)
);
insert into users (username, password, email)
values
('user1', '$2y$12$KQvocrh3mFd3Kq3YhF3bVebVAQkHTukjXKY.5z8tuDatxKQqqIac2', 'user1@gmail.com'),
('user2', '$2y$12$KQvocrh3mFd3Kq3YhF3bVebVAQkHTukjXKY.5z8tuDatxKQqqIac2', 'user2@gmail.com');

insert into roles (name)
values
('ROLE_USER'),
('ROLE_ADMIN');

insert into users_roles (user_id, role_id)
values
(1,1),
(2,2);

insert into product (title, cost)
values ('Картошка', 10),
       ('Капуста', 20),
       ('Макароны', 30),
       ('Огурцы', 40),
       ('Помидроры', 50),
       ('Катлеты', 60),
       ('Пельмени', 70),
       ('Редиска', 80),
       ('Малина', 90),
       ('Смородина', 100),
       ('Арбуз', 110),
       ('Апельсин', 120),
       ('Укроп', 130),
       ('Ананас', 140),
       ('Бакложан', 150),
       ('Банан', 160),
       ('Хлеб', 170),
       ('Гречка', 180),
       ('Дыня', 190),
       ('Картошка', 10),
       ('Капуста', 20),
       ('Макароны', 30),
       ('Огурцы', 40),
       ('Помидроры', 50),
       ('Катлеты', 60),
       ('Пельмени', 70),
       ('Редиска', 80),
       ('Малина', 90),
       ('Смородина', 100),
       ('Арбуз', 110),
       ('Апельсин', 120),
       ('Укроп', 130),
       ('Ананас', 140),
       ('Бакложан', 150),
       ('Банан', 160),
       ('Хлеб', 170),
       ('Гречка', 180),
       ('Дыня', 190),
       ('Картошка', 10),
       ('Капуста', 20),
       ('Макароны', 30),
       ('Огурцы', 40),
       ('Помидроры', 50),
       ('Катлеты', 60),
       ('Пельмени', 70),
       ('Редиска', 80),
       ('Малина', 90),
       ('Смородина', 100),
       ('Арбуз', 110),
       ('Апельсин', 120),
       ('Укроп', 130),
       ('Ананас', 140),
       ('Бакложан', 150),
       ('Банан', 160),
       ('Хлеб', 170),
       ('Гречка', 180),
       ('Дыня', 190),
       ('Картошка', 10),
       ('Капуста', 20),
       ('Макароны', 30),
       ('Огурцы', 40),
       ('Помидроры', 50),
       ('Катлеты', 60),
       ('Пельмени', 70),
       ('Редиска', 80),
       ('Малина', 90),
       ('Смородина', 100),
       ('Арбуз', 110),
       ('Апельсин', 120),
       ('Укроп', 130),
       ('Ананас', 140),
       ('Бакложан', 150),
       ('Банан', 160),
       ('Хлеб', 170),
       ('Гречка', 180),
       ('Дыня', 190),
       ('Киви', 200);

