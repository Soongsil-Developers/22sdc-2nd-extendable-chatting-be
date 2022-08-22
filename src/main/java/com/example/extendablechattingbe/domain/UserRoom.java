package com.example.extendablechattingbe.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@SQLDelete(sql = "UPDATE user_room SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is NULL")
@Entity
public class UserRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
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

}
