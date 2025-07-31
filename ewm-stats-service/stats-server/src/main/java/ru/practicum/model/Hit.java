package ru.practicum.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.constant.StatisticConstant;

import java.time.LocalDateTime;

import static ru.practicum.constant.StatisticConstant.DATE_TIME_FORMATTER;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hits")
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String app;

    @Column(nullable = false, length = 64)
    private String uri;
    @Column(nullable = false)
    private String ip;

    @Column(name = "created", nullable = false)
    @JsonFormat(pattern = StatisticConstant.DATE_TIME_FORMATTER)
    @DateTimeFormat(pattern = DATE_TIME_FORMATTER)
    private LocalDateTime timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hit)) return false;
        return id != null && id.equals(((Hit) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
