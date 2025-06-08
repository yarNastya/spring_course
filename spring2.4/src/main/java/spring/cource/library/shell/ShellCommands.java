package spring.cource.library.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import spring.cource.library.dto.Book;
import spring.cource.library.service.AuthorService;
import spring.cource.library.service.BookService;
import spring.cource.library.service.CommentService;
import spring.cource.library.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    private final CommentService commentService;

    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption String userName) {
        if (isNotBlank(userName)) {
            this.userName = userName;
            return String.format("Добро пожаловать, %s", userName);
        } else {
            return "Пожалуйста, введите свое имя";
        }
    }

    @ShellMethod(value = "Get book count", key = {"c", "count"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String getBookCount() {
        return String.format("Количество книг в библиотеке: % d", bookService.count());
    }

    @ShellMethod(value = "Get all books", key = {"a", "all"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String getAllBooks() {
        List<Book> bookList = bookService.getAll();
        return String.format("Список книг: %n%s%n",
                bookList.stream().map(Book::toString).collect(Collectors.joining("\n")));

    }

    @ShellMethod(value = "Get book by id", key = {"get"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String getBookById(@ShellOption Long id) {
        return bookService.getById(id);
    }

    @ShellMethod(value = "Add new book", key = {"add"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String insertBook(@ShellOption String name,
                             @ShellOption(value = "a", help = "Id of author") long authorId,
                             @ShellOption(value = "g", help = "Id of genre") long genreId,
                             @ShellOption int year) {
        return bookService.insertBook(name, authorId, genreId, year);

    }

    @ShellMethod(value = "Add new author", key = {"aavt"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String insertAuthor(@ShellOption String name,
                               @ShellOption(value = "pat", help = "patronymic") String patronymic,
                               @ShellOption(value = "sur", help = "surname") String surname) {
        return authorService.insertAuthor(name, patronymic, surname);

    }

    @ShellMethod(value = "Add new genre", key = {"agen"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String insertGenre(@ShellOption String name) {
        return genreService.insertGenre(name);

    }

    @ShellMethod(value = "Update book", key = {"update"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String updateBook(@ShellOption long id,
                             @ShellOption(defaultValue = "__NULL__") String name,
                             @ShellOption(value = "a", help = "Id of author", defaultValue = "__NULL__") Long authorId,
                             @ShellOption(value = "g", help = "Id of genre", defaultValue = "__NULL__") Long genreId,
                             @ShellOption(defaultValue = "__NULL__") Integer year) {
        return bookService.updateBook(id, name, authorId, genreId, year);

    }

    @ShellMethod(value = "Delete book", key = {"delete"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String updateBook(
            @ShellOption long id) {
        return bookService.deleteBook(id);

    }

    @ShellMethod(value = "Add new comment", key = {"acom"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String insertComment(@ShellOption String text,
                                @ShellOption(value = "b", help = "Id of book") Long bookId) {
        return commentService.insertComment(text, bookId);
    }

    @ShellMethod(value = "Delete new comment", key = {"dcom"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String insertComment(@ShellOption long id) {
        return commentService.deleteComment(id);
    }

    @ShellMethod(value = "Update comment", key = {"ucom"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String updateBook(@ShellOption long id,
                             @ShellOption(defaultValue = "__NULL__") String text) {
        return commentService.updateComment(id, text);
    }

    @ShellMethod(value = "Get all comments by book", key = {"allcom"})
    @ShellMethodAvailability(value = "isWorkWithLibraryAvailable")
    public String updateBook(@ShellOption(value = "b", help = "Id of book") Long bookId) {
        return commentService.getCommentsByBookId(bookId);
    }

    private Availability isWorkWithLibraryAvailable() {
        return userName == null ? Availability.unavailable("Log in first") : Availability.available();
    }
}
