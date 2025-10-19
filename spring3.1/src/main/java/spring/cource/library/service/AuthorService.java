package spring.cource.library.service;

import spring.cource.library.domain.Author;
import spring.cource.library.dto.AuthorDto;

import java.util.Optional;

public interface AuthorService {

    AuthorDto insertAuthor(String name, String patronymic, String surname);
}
