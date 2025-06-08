package spring.cource.library.service;

public interface CommentService {
    String insertComment(String text, long bookId);

    String deleteComment(long commentId);

    String updateComment(long id, String newText);

    String getCommentsByBookId(long bookId);
}
