package spring.cource.library.service;

import spring.cource.library.domain.Comment;
import spring.cource.library.dto.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentDto insertComment(String text, long bookId);

   boolean deleteComment(long commentId);

    CommentDto updateComment(long id, String newText);

    List<CommentDto> getCommentsByBookId(long bookId);
}
