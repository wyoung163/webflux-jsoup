package com.example.webflux.Domain;

import lombok.*;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Table("user")
public class User implements UserDetails {
    @Id
    private int id;

    private long kakao_id;

    //private String name;

    private String nickname;

    private String email;

    @CreatedDate
    private LocalDateTime created_at;

    @LastModifiedDate
    private LocalDateTime updated_at;

    @Transient
    private List<String> roles = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.nickname;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
