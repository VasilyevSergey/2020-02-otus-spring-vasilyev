package com.otus.homework.dao;

import com.otus.homework.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class GenreDaoJdbc implements GenreDao {

    private static final String ID = "id";
    private static final String NAME = "name";

    private final NamedParameterJdbcOperations namedJdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations namedJdbc) {
        this.namedJdbc = namedJdbc;
    }

    @Override
    public int count() {
        return namedJdbc.getJdbcOperations().queryForObject("select count(*) from genres", Integer.class);
    }

    @Override
    public void insert(Genre genre) {
        Map<String, Object> params = Map.of(
                ID, genre.getId(),
                NAME, genre.getName()
        );

        namedJdbc.update("insert into genres(id, name) values (:id, :name)", params);
    }

    @Override
    public Genre getById(Long id) {
        return namedJdbc.queryForObject("select * from genres where id = :id", Map.of(ID, id), new GenreMapper());
    }

    @Override
    public void deleteById(Long id) {
        namedJdbc.update("delete from genres where id = :id", Map.of(ID, id));
    }

    @Override
    public List<Genre> getAll() {
        return namedJdbc.getJdbcOperations().query("select * from genres", new GenreMapper());
    }


    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong(ID);
            String name = rs.getString(NAME);
            return new Genre(id, name);
        }
    }
}
