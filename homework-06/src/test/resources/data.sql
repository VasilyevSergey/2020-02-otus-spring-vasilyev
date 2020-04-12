insert into authors (id, name) values (1, 'Pushkin');
insert into authors (id, name) values (2, 'Tolkien');

insert into genres (id, name) values (1, 'A poem in verse');
insert into genres (id, name) values (2, 'Fantasy');

insert into books (id, title, author_id, genre_id) values (1, 'Ruslan and Lyudmila', 1, 1);
insert into books (id, title, author_id, genre_id) values (2, 'Lord of the Rings', 2, 2);
