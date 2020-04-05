package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import com.otus.homework.domain.Book;
import com.otus.homework.domain.Genre;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class BookDaoJdbc implements BookDao {
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String AUTHOR_ID = "author_id";
    private static final String GENRE_ID = "genre_id";
    private static final String GENRE_NAME = "genre_name";
    private static final String AUTHOR_NAME = "author_name";

    private final JdbcOperations jdbc;
    private final NamedParameterJdbcOperations namedJdbc;

    public BookDaoJdbc(JdbcOperations jdbcOperations, NamedParameterJdbcOperations namedJdbc) {
        this.jdbc = jdbcOperations;
        this.namedJdbc = namedJdbc;
    }

    @Override
    public int count() {
        return jdbc.queryForObject("select count(*) from books", Integer.class);
    }

    @Override
    public void insert(Book book) {
        Map<String, Object> params = Map.of(
                ID, book.getId(),
                TITLE, book.getTitle(),
                AUTHOR_ID, book.getAuthor().getId(),
                GENRE_ID, book.getGenre().getId()
        );

        namedJdbc.update("insert into books(id, title, author_id, genre_id) values (:id, :title, :author_id, :genre_id)", params);
    }

    @Override
    public int updateById(Long id, String newTitle, Author newAuthor, Genre newGenre) {
        Map<String, Object> params = Map.of(
                ID, id,
                TITLE, newTitle,
                AUTHOR_ID, newAuthor.getId(),
                GENRE_ID, newGenre.getId()
        );

        return namedJdbc.update(
                "update books set title = :title, author_id = :author_id, genre_id = :genre_id where id = :id",
                params);
    }

    @Override
    public Book getById(Long id) {
        return namedJdbc.queryForObject(
                "select b.*, a.name as author_name, g.name as genre_name " +
                        "from books b, authors a, genres g " +
                        "where b.author_id = a.id " +
                        "and b.genre_id = g.id " +
                        "and b.id = :id",
                Map.of(ID, id),
                new BookMapper());
    }

    @Override
    public void deleteById(Long id) {
        namedJdbc.update("delete from books where id = :id", Map.of(ID, id));
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
                "select b.*, a.name as author_name, g.name as genre_name " +
                        "from books b, authors a, genres g " +
                        "where b.author_id = a.id " +
                        "and b.genre_id = g.id",
                new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong(ID);
            String name = rs.getString(TITLE);
            Author author = new Author(rs.getLong(AUTHOR_ID), rs.getString(AUTHOR_NAME));
            Genre genre = new Genre(rs.getLong(GENRE_ID), rs.getString(GENRE_NAME));
            return new Book(id, name, author, genre);
        }
    }
}
