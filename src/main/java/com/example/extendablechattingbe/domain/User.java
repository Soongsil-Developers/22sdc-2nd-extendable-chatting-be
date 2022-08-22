package com.example.extendablechattingbe.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Getter
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is NULL")
@Table(name = "users", indexes = {
        @Index(columnList = "userName", unique = true),
})
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Setter
    @Column(nullable = false, length = 100)
    private String userName;

    @Setter
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<UserRoom> userRooms = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    void createdAt() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    void modifiedAt() {
        modifiedAt = LocalDateTime.now();
    }

    protected User() {
    }

    private User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static User of(String userName, String password) {
        return new User(userName, password);
    }

}
