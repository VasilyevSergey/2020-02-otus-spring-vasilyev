drop table if exists books;
drop table if exists authors;
drop table if exists genres;

create table authors(id bigint auto_increment primary key, name varchar(255));
create table genres(id bigint auto_increment primary key, name varchar(255));
create table books(
    id bigint auto_increment primary key,
    title varchar(255),
    author_id bigint,
    genre_id bigint,
    constraint fk_author
        foreign key (author_id)
            references authors(id) on delete cascade,
    constraint fk_genre
        foreign key (genre_id)
            references genres(id) on delete cascade,
    unique (title, author_id, genre_id));


