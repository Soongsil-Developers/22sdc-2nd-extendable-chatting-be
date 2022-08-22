package com.example.extendablechattingbe.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@SQLDelete(sql = "UPDATE room SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is NULL")
@Table(indexes = {
        @Index(columnList = "roomName", unique = true),
})
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String roomName;

    private String password;

    @OneToMany(mappedBy = "room")
    private List<Chat> chats = new ArrayList<>();

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

    protected Room() {
    }

    private Room(String roomName, String password) {
        this.roomName = roomName;
        this.password = password;
    }

    public static Room of(String roomName, String password) {
        return new Room(roomName, password);
    }

}
