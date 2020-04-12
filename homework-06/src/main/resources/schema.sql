drop table if exists books;
drop table if exists authors;
drop table if exists genres;

create table authors(
    id bigint auto_increment,
    name varchar(255),
    primary key (id)
);

create table genres(
    id bigint auto_increment,
    name varchar(255),
    primary key (id)
);

create table books(
    id bigint auto_increment,
    title varchar(255),
    author_id bigint references authors(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id),
    unique (title, author_id, genre_id));


