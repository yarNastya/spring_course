package spring.cource.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import spring.cource.library.domain.Comment;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CommentDto {

    private long id;
    private String text;
    private BookDto book;

    public static Comment toDomainObject(CommentDto commentDto) {
        return new Comment(commentDto.getId(), commentDto.getText(), BookDto.toDomainObject(commentDto.getBook()));
    }

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), BookDto.toDtoSimple(comment.getBook()));
    }
}
