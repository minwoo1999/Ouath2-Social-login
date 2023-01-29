package com.example.security1.model;


import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@DynamicInsert //@DynamicInsert사용
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String email;
    @ColumnDefault("'ROLE_USER'")
    private String role; //ROLE_USER,ROLE_ADMIN

    private String provider; //예: google

    private String providerId;// 예:) sub=102754714284297216511

    @CreatedDate
    private LocalDateTime createDate;

    @Builder
    public User(long id, String username, String password, String email, String role, String provider, String providerId, LocalDateTime createDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }
}
