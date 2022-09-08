package com.example.extendablechattingbe.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE room SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is NULL")
@Table(indexes = {
        @Index(columnList = "room_name", unique = true),
})
@Entity
public class Room extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "room_name", nullable = false, length = 100)
    private String roomName;

    @Column(name = "room_content")
    private String roomContent;

    // 참여 인원
    @Column(name = "limit_user_count")
    private int limitUserCount;



    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id desc")
    private Set<Chat> chats = new LinkedHashSet<>();

    @Column(name = "deleted_at")
    private LocalDateTime deletedTime;

    @Builder(access = AccessLevel.PRIVATE)
    private Room(String roomName, String roomContent, int limitUserCount) {
        this.roomName = roomName;
        this.roomContent = roomContent;
        this.limitUserCount = limitUserCount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id.equals(room.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
