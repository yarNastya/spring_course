package spring.cource.library.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.dto.Genre;
import spring.cource.library.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceConsole implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public String insertGenre(String name) {
        try {
            Genre genre = genreRepository.save(new Genre().setName(name));
            return String.format("Добавлен новый жанр с id = %d", genre.getId());

        } catch (Exception ex) {
            return "Не удалось добавить жанр в библиотеку";
        }
    }
}
