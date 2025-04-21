package spring.cource.library.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.repository.GenreRepository;
import spring.cource.library.dto.Genre;

@Service
@RequiredArgsConstructor
public class GenreServiceConsole implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public String insertGenre(String name) {
        try {
            Genre genre = new Genre().setName(name);
            return genreRepository.insert(genre).map(id -> String.format("Добавлен новый жанр с id = %d", id))
                    .orElse("Не удалось добавить жанр в библиотеку");

        } catch (Exception ex) {
            return "Не удалось добавить жанр в библиотеку";
        }
    }
}
