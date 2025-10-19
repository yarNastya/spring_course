package spring.cource.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.domain.Author;
import spring.cource.library.domain.Book;
import spring.cource.library.domain.Genre;
import spring.cource.library.dto.BookDto;
import spring.cource.library.exception.CouldNotSaveException;
import spring.cource.library.exception.NotFoundException;
import spring.cource.library.repository.AuthorRepository;
import spring.cource.library.repository.BookRepository;
import spring.cource.library.repository.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class BookServiceRest implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return bookRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getAll() {
        return bookRepository.findAll().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()->new NotFoundException("Книга не найдена по ИД"));
        return BookDto.toDto(book);

    }

    @Override
    @Transactional
    public BookDto insertBook(String name, long authorId, long genreId, int year) {
        try {
            Optional<Author> author = authorRepository.findById(authorId);
            if (author.isEmpty()) {
                throw new NotFoundException("Автор не найден по id");
            }
            Optional<Genre> genre = genreRepository.findById(genreId);
            if (genre.isEmpty()) {
                throw new NotFoundException("Жанр не найден по id");
            }
            Book book = bookRepository.save(new Book().setName(name).setAuthor(author.get()).setGenre(genre.get()).setYear(year));
            return BookDto.toDto(book);

        } catch (Exception ex) {
            throw new CouldNotSaveException("Не удалось добавить книгу в библиотеку");
        }
    }

    @Override
    @Transactional
    public BookDto updateBook(long id, String name, Long authorId, Long genreId, Integer year) {
        try {
            Optional<Book> optionalBook = bookRepository.findById(id);
             if (optionalBook.isEmpty()) {
                 throw new NotFoundException("Не удалось найти книгу по id");
                   }
            Book book = optionalBook.get();
            if (nonNull(name)) {
                book.setName(name);
            }
            if (nonNull(year)) {
                book.setYear(year);
            }
            if (nonNull(authorId)) {
                Optional<Author> optionalAuthor = authorRepository.findById(authorId);
                if (optionalAuthor.isEmpty()) {
                    throw new NotFoundException("Автор не найден по id");

                } else {
                    book.setAuthor(optionalAuthor.get());
                }
            }
            if (nonNull(genreId)) {
                Optional<Genre> optionalGenre = genreRepository.findById(genreId);
                if (optionalGenre.isEmpty()) {
                    throw new NotFoundException("Жанр не найден по id");
                } else {
                    book.setGenre(optionalGenre.get());
                }
            }
            Book updatedBook = bookRepository.save(book);
            return BookDto.toDto(updatedBook);

        } catch (Exception ex) {
            throw new CouldNotSaveException("Не удалось обновить данные книги");
        }
    }

    @Override
    @Transactional
    public BookDto updateBook(long id, BookDto bookDto) {
        try {
            Book book= BookDto.toDomainObject(bookDto);
            Optional<Book> optionalBook = bookRepository.findById(id);
            if (optionalBook.isEmpty()) {
                throw new NotFoundException("Не удалось найти книгу по id");
            }
            if (nonNull(book.getAuthor()) && nonNull(book.getAuthor().getId())) {
                Optional<Author> optionalAuthor = authorRepository.findById(book.getAuthor().getId());
                if (optionalAuthor.isEmpty()) {
                    throw new NotFoundException("Автор не найден по id");
                } else {
                    book.setAuthor(optionalAuthor.get());
                }
            }
            if (nonNull(book.getGenre()) && nonNull(book.getGenre().getId())) {
                Optional<Genre> optionalGenre = genreRepository.findById(book.getGenre().getId());
                if (optionalGenre.isEmpty()) {
                    throw new NotFoundException("Жанр не найден по id");
                } else {
                    book.setGenre(optionalGenre.get());
                }
            }
            Book updatedBook = bookRepository.save(book);
            return BookDto.toDto(updatedBook);

        } catch (Exception ex) {
            throw new CouldNotSaveException("Не удалось обновить данные книги");
        }
    }

    @Override
    @Transactional
    public boolean deleteBook(long id) {
        try {
            bookRepository.deleteById(id);
            return true;
        } catch (Exception ex) {
            throw new CouldNotSaveException("Не удалось удалить книгу");
        }
    }

    @Override
    @Transactional
    public BookDto insertBook(BookDto bookDto) {
        try {
            Book book = BookDto.toDomainObject(bookDto);
            Book newBook = bookRepository.save(book);
            return BookDto.toDto(newBook);

        } catch (Exception ex) {
            throw new CouldNotSaveException("Не удалось добавить книгу в библиотеку");
        }
    }
}
