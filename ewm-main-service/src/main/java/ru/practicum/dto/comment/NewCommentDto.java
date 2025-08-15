package ru.practicum.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class NewCommentDto {
    @NotBlank
    @Size(min = 10, max = 7000)
    private String text;
}
