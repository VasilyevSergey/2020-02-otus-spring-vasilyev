insert into authors (id, name) values (1, 'Пушкин');
insert into authors (id, name) values (2, 'Толкиен');

insert into genres (id, name) values (1, 'Поэма в стихах');
insert into genres (id, name) values (2, 'Фэнтези');

insert into books (id, title, author_id, genre_id) values (1, 'Руслан и Людмила', 1, 1);
insert into books (id, title, author_id, genre_id) values (2, 'Властелин колец', 2, 2);
