insert into authors (id, name) values (1, 'Pushkin');
insert into authors (id, name) values (2, 'Tolkien');

insert into genres (id, name) values (1, 'A poem in verse');
insert into genres (id, name) values (2, 'Fantasy');

insert into books (id, title, author_id, genre_id) values (1, 'Ruslan and Lyudmila', 1, 1);
insert into books (id, title, author_id, genre_id) values (2, 'Lord of the Rings', 2, 2);

insert into comments (id, comment, datetime, book_id, commentator)
values (1, 'Cool', TIMESTAMP WITH TIME ZONE '2020-01-01 00:00:01Z', 1, 'Vasya');

insert into comments (id, comment, datetime, book_id, commentator)
values (2, 'Nice',  TIMESTAMP WITH TIME ZONE '2020-01-02 00:00:01Z', 1, 'Marusya');

insert into comments (id, comment, datetime, book_id, commentator)
values (3, 'Brilliant',  TIMESTAMP WITH TIME ZONE '2020-01-03 00:00:01Z', 2, 'Petya');
