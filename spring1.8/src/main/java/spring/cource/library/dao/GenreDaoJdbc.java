package spring.cource.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import spring.cource.library.dto.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public Optional<Long> insert(Genre genre) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("name", genre.getName());
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbc.update("insert into genres(name) values(:name)",
                    params,
                    keyHolder);
            return Optional.of(keyHolder.getKey().longValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<Genre> getById(long id) {
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            return Optional.of(jdbc.queryForObject(
                    "select id, name from genres where id = :id", params, new GenreMapper())
            );
        } catch (Exception e) {
            return Optional.empty();
        }

    }

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
