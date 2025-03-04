package spring.cource.library.dao;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import spring.cource.library.dto.Author;
import spring.cource.library.dto.Book;
import spring.cource.library.dto.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from books", Integer.class);
    }

    @Override
    public Optional<Long> insert(Book book) {
        try {
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("name", book.getName());
            params.addValue("author_id", book.getAuthor().getId());
            params.addValue("genre_id", book.getGenre().getId());
            params.addValue("year", book.getYear());
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbc.update("insert into books(name,author_id,genre_id,publication_year) values(:name,:author_id,:genre_id,:year)",
                    params,
                    keyHolder);
            return Optional.of(keyHolder.getKey().longValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> update(Book book) {
        try{
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        params.addValue("year", book.getYear());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("update books set name=:name, author_id=:author_id,genre_id=:genre_id, publication_year=:year where id=:id",
                params,keyHolder);
            return Optional.of(keyHolder.getKey().longValue());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public void deleteById(long id) {
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        jdbc.update("delete from books where id=:id", params);
    }

    @Override
    public Optional<Book> getById(long id) {
        try {
            Map<String, Object> params = Collections.singletonMap("id", id);
            return  Optional.of(jdbc.queryForObject(
                    "select id, name, author_id, genre_id, publication_year from books where id = :id", params, new BookMapper()
            ));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query("select id, name, author_id, genre_id, publication_year from books", new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            int year = resultSet.getInt("publication_year");

            long author_id = resultSet.getLong("author_id");
            long genre_id = resultSet.getLong("genre_id");
            return new Book(id, name, new Author().setId(author_id) , new Genre().setId(genre_id), year);
        }
    }
}
