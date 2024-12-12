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
@RequiredArgsConstructor
@Table("user")
public class User {
    @Id
    private int id;

    private long kakao_id;

    //private String name;

    private String nickname;

    private String email;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
