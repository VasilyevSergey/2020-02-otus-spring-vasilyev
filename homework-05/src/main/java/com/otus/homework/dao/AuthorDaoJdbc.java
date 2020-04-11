package com.otus.homework.dao;

import com.otus.homework.domain.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private static final String ID = "id";
    private static final String NAME = "name";

    private final NamedParameterJdbcOperations namedJdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedJdbc) {
        this.namedJdbc = namedJdbc;
    }


    @Override
    public int count() {
        return namedJdbc.getJdbcOperations().queryForObject("select count(*) from authors", Integer.class);
    }

    @Override
    public void insert(Author author) {
        Map<String, Object> params = Map.of(NAME, author.getName());
        namedJdbc.update("insert into authors(name) values (:name)", params);
    }

    @Override
    public Author getById(Long id) {
        return namedJdbc.queryForObject("select * from authors where id = :id", Map.of(ID, id), new AuthorMapper());
    }

    @Override
    public void deleteById(Long id) {
        namedJdbc.update("delete from authors where id = :id", Map.of(ID, id));
    }

    @Override
    public List<Author> getAll() {
        return namedJdbc.getJdbcOperations().query("select * from authors", new AuthorMapper());
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong(ID);
            String name = rs.getString(NAME);
            return new Author(id, name);
        }
    }
}
