package spring.cource.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.cource.library.dto.BookDto;
import spring.cource.library.service.BookService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(value = "/books/")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAll());
    }

    @GetMapping(value = "/books/count")
    public ResponseEntity<Long> getBookCount() {
        return ResponseEntity.ok(bookService.count());
    }

    @GetMapping(value = "/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PostMapping(value = "/books")
    public ResponseEntity<BookDto> addNewBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.insertBook(bookDto));
    }


    @PutMapping(value = "/books/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") long id,
                                              @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDto));
    }

    @DeleteMapping(value = "/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
