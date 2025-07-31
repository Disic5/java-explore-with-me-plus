package ru.practicum.mappers;

import ru.practicum.HitDto;
import ru.practicum.model.Hit;

import java.time.LocalDateTime;

public class HitMapper {

    private HitMapper() {
    }

    public static Hit toHit(HitDto hitDto) {
        return new Hit(hitDto.getId(), hitDto.getApp(), hitDto.getUri(), hitDto.getIp(), LocalDateTime.now());
    }

    public static HitDto toHitDto(Hit hit) {
        return new HitDto(hit.getId(), hit.getApp(), hit.getUri(), hit.getIp(), hit.getTimestamp());
    }
}
