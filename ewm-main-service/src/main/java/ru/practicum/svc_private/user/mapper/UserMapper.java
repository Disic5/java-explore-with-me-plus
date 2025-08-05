package ru.practicum.svc_private.user.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.svc_private.user.dto.UserDto;
import ru.practicum.svc_private.user.model.User;

@Component
public class UserMapper {
    public UserDto toUserDto(final User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User toUser(final UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }
}
