package spring.cource.library.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import spring.cource.library.dto.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public Optional<Author> getById(long id) {
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            Author author = jdbc.queryForObject(
                    "select id, name, patronymic, surname from authors where id = :id", params, new AuthorMapper()
            );
           return Optional.of(author);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> insert(Author author) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("name", author.getName());
            params.addValue("surname", author.getSurname());
            params.addValue("patronymic", author.getPatronymic());
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbc.update("insert into authors(name,patronymic,surname) values(:name,:patronymic,:surname)",
                    params,
                    keyHolder);
            return Optional.of(keyHolder.getKey().longValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String patronymic = resultSet.getString("patronymic");
            String surname = resultSet.getString("surname");
            return new Author(id, name, patronymic, surname);
        }
    }
}
