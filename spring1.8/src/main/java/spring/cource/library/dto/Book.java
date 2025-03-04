package spring.cource.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Book {


    private long id;

    private String name;

    @MappedCollection(idColumn = "id")
    private Author author;

    private Genre genre;

    private int year;

    public Book(long id, String name)
    {
        this.id = id;
        this.name = name;
    }


}
