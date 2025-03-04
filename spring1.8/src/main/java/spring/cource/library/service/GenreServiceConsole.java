package spring.cource.library.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.cource.library.dao.GenreDao;
import spring.cource.library.dto.Genre;

@Service
@RequiredArgsConstructor
public class GenreServiceConsole implements GenreService {

    private final GenreDao genreDao;

    @Override
    public String insertGenre(String name) {
        try {
            Genre genre = new Genre().setName(name);
            return genreDao.insert(genre).map(id -> String.format("Добавлен новый жанр с id = %d", id))
                    .orElse("Не удалось добавить жанр в библиотеку");

        } catch (Exception ex) {
            return "Не удалось добавить жанр в библиотеку";
        }
    }
}
