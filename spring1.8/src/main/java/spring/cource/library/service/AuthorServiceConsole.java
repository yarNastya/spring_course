package spring.cource.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.cource.library.dao.AuthorDao;
import spring.cource.library.dto.Author;

@Service
@RequiredArgsConstructor
public class AuthorServiceConsole implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public String insertAuthor(String name, String patronymic, String surname) {
        try {
            Author author = new Author().setName(name).setPatronymic(patronymic).setSurname(surname);
            return authorDao.insert(author).map(id -> String.format("Добавлен новый автор с id = %d", id))
                    .orElse("Не удалось добавить автора в библиотеку");

        } catch (Exception ex) {
            return "Не удалось добавить автора в библиотеку";
        }
    }
}
