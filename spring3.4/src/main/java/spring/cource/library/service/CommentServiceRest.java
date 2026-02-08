package spring.cource.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.domain.Book;
import spring.cource.library.domain.Comment;
import spring.cource.library.dto.CommentDto;
import spring.cource.library.exception.CouldNotSaveException;
import spring.cource.library.exception.NotFoundException;
import spring.cource.library.repository.BookRepository;
import spring.cource.library.repository.CommentRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CommentServiceRest implements CommentService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentDto insertComment(String text, long bookId) {
        try {
            Optional<Book> book = bookRepository.findById(bookId);
            if (book.isEmpty()) {
                throw new NotFoundException("Книга не найдена id");
            }
            Comment comment = commentRepository.save(new Comment().setText(text).setBook(book.get()));
            return CommentDto.toDto(comment);

        } catch (Exception ex) {
            throw new CouldNotSaveException("Не удалось добавить комментарий к книге");
        }
    }

    @Override
    @Transactional
    public boolean deleteComment(long commentId) {
        try {
            commentRepository.deleteById(commentId);
            return true;

        } catch (Exception ex) {
            throw new CouldNotSaveException("Не удалось удалить комментарий");
        }
    }

    @Override
    @Transactional
    public CommentDto updateComment(long id, String newText) {
        try {
            Optional<Comment> optionalComment = commentRepository.findById(id);
            if (optionalComment.isEmpty()) {
                throw new NotFoundException("Не удалось найти комментарий по id");
            }
            Comment comment = optionalComment.get();
            if (nonNull(newText)) {
                comment.setText(newText);
            }
            Comment updatedComment = commentRepository.save(comment);
            return CommentDto.toDto(updatedComment);

        } catch (Exception ex) {
            throw new CouldNotSaveException("Не удалось обновить комментарий");
        }
    }

    public List<CommentDto> getCommentsByBookId(long bookId) {
        return commentRepository.findByBookId(bookId).stream().map(CommentDto::toDto).collect(Collectors.toList());
    }
}
