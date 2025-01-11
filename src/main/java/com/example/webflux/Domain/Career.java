package com.example.webflux.Domain;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table("career")
public class Career {
    @Id
    private int id;

    private int company;

    private String category;

    private String title;

    private String content;

    private String requirements;

    private String preference;

    private LocalDateTime start;

    private LocalDateTime end;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;
}
