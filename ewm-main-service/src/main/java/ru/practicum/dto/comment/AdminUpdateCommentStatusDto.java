package ru.practicum.dto.comment;

import lombok.Data;
import ru.practicum.model.enums.AdminUpdateCommentStatusAction;

@Data
public class AdminUpdateCommentStatusDto {
    private AdminUpdateCommentStatusAction action;
}
