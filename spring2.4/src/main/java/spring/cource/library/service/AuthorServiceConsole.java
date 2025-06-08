package spring.cource.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.dto.Author;
import spring.cource.library.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceConsole implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public String insertAuthor(String name, String patronymic, String surname) {
        try {
            Author author = authorRepository.save(new Author().setName(name).setPatronymic(patronymic).setSurname(surname));
            return  String.format("Добавлен новый автор с id = %d", author.getId());
        } catch (Exception ex) {
            return "Не удалось добавить автора в библиотеку";
        }
    }
}
