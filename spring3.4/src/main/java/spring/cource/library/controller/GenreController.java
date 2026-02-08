package spring.cource.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spring.cource.library.dto.GenreDto;
import spring.cource.library.service.GenreService;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping(value = "/genres")
    public ResponseEntity<GenreDto> addNewGenre(@RequestParam("name") String name
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.insertGenre(name));
    }

}
