package com.example.webflux.Domain;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

//@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table("career")
public class Career {
    @Id
    private int id;

    private String company;

    private String category;

    private String team;

    private String title;

    private String content; // 모집 내용

    private String link; // 상세 공고 주소

    private String experience; // 경력 여부

    private String conditions; // 근로 조건

    private String requirements;

    private String preference;

    private String duration;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;
}
