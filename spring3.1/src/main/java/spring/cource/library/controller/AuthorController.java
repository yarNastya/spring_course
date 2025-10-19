package spring.cource.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.cource.library.dto.AuthorDto;
import spring.cource.library.service.AuthorService;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping(value = "/authors")
    public ResponseEntity<AuthorDto> addNewAuthor(@RequestParam("name") String name,
                                                  @RequestParam("patronymic") String patronymic,
                                                  @RequestParam("surname") String surname) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.insertAuthor(name, patronymic, surname));
    }


}
