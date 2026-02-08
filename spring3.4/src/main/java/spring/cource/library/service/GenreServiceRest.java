package spring.cource.library.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.domain.Genre;
import spring.cource.library.dto.GenreDto;
import spring.cource.library.exception.CouldNotSaveException;
import spring.cource.library.repository.GenreRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceRest implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public GenreDto insertGenre(String name) {
        try {
            Genre genre = genreRepository.save(new Genre().setName(name));
            return GenreDto.toDto(genre);

        } catch (Exception ex) {
            throw new CouldNotSaveException("Не удалось добавить жанр в библиотеку");
        }
    }
}
