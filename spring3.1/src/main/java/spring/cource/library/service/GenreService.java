package spring.cource.library.service;

import spring.cource.library.dto.GenreDto;

import java.util.Optional;

public interface GenreService {

    GenreDto insertGenre(String name);
}
