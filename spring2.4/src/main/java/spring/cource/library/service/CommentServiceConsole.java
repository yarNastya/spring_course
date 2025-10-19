package spring.cource.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.dto.Book;
import spring.cource.library.dto.Comment;
import spring.cource.library.repository.BookRepository;
import spring.cource.library.repository.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CommentServiceConsole implements CommentService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public String insertComment(String text, long bookId) {
        try {
            Optional<Book> book = bookRepository.findById(bookId);
            if (book.isEmpty()) {
                return "Книга не найдена id";
            }
            Comment comment = commentRepository.save(new Comment().setText(text).setBook(book.get()));

            return String.format("Добавлен комментарий с id = %d", comment.getId());

        } catch (Exception ex) {
            return "Не удалось добавить комментарий к книге";
        }
    }

    @Override
    @Transactional
    public String deleteComment(long commentId) {
        try {
            commentRepository.deleteById(commentId);

            return "Комментарий удален";

        } catch (Exception ex) {
            return "Не удалось удалить комментарий";
        }
    }

    @Override
    @Transactional
    public String updateComment(long id, String newText) {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (optionalComment.isEmpty()) {
                return "Не удалось найти комментарий по id";
            }
            Comment comment = optionalComment.get();
            if (nonNull(newText)) {
                comment.setText(newText);
            }
            commentRepository.save(comment);
            return String.format("Текст комментарий c id = %d обновлен", id);

        } catch (Exception ex) {
            return "Не удалось обновить комментарий";
        }
    }

    public String getCommentsByBookId(long bookId) {
        List<Comment> comments = commentRepository.findByBookId(bookId);
        return String.format("Список комментариев: %n%s%n",
                comments.stream().map(Comment::getText).collect(Collectors.joining("\n")));
    }
}
