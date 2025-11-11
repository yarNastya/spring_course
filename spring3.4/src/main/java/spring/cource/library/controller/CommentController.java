package spring.cource.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.cource.library.dto.CommentDto;
import spring.cource.library.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = "/books/comments")
    public ResponseEntity<CommentDto> addNewComment(@RequestParam("text") String text,
                                                    @RequestParam("bookId") long bookId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.insertComment(text, bookId));
    }

    @PutMapping(value = "/books/{bookId}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("bookId") long bookId,
                                                    @PathVariable("id") long id,
                                                    @RequestParam("text") String text
    ) {
        return ResponseEntity.ok(commentService.updateComment(id, text));
    }

    @GetMapping(value = "/books/{bookId}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsByBookId(@PathVariable("bookId") long bookId) {
        return ResponseEntity.ok(commentService.getCommentsByBookId(bookId));
    }

}
