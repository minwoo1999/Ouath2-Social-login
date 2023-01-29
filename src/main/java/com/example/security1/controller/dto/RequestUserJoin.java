package com.example.security1.controller.dto;

import com.example.security1.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserJoin {
    private long id;

    private String email;
    private String password;
    private String username;

    private String role; //ROLE_USER,ROLE_ADMIN

    private LocalDateTime createDate;

    public User toEntity(){

        User build=User.builder()
                .id(id)
                .email(email)
                .password(password)
                .username(username)
                .createDate(createDate)
                .build();
        return build;

    }

}
