package spring.cource.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.domain.Author;
import spring.cource.library.dto.AuthorDto;
import spring.cource.library.exception.CouldNotSaveException;
import spring.cource.library.repository.AuthorRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceRest implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public AuthorDto insertAuthor(String name, String patronymic, String surname) {
        try {
            Author author = authorRepository.save(new Author().setName(name).setPatronymic(patronymic).setSurname(surname));
            return  AuthorDto.toDto(author);
        } catch (Exception ex) {
            throw new CouldNotSaveException("Не удалось добавить автора в библиотеку");
        }
    }
}
