drop table if exists authors;
create table authors(id bigint primary key, name varchar(255));

drop table if exists genres;
create table genres(id bigint primary key, name varchar(255));

drop table if exists books;
create table books(
    id bigint primary key,
    title varchar(255),
    author_id bigint,
    genre_id bigint,
    constraint fk_author
        foreign key (author_id)
            references authors(id) on delete cascade,
    constraint fk_genre
        foreign key (genre_id)
            references genres(id) on delete cascade);


