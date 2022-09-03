package com.example.extendablechattingbe.room.entity;

import com.example.extendablechattingbe.chat.entity.Chat;
import com.example.extendablechattingbe.common.entity.BaseTimeEntity;
import com.example.extendablechattingbe.participant.entity.Participant;
import com.example.extendablechattingbe.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_user_id")
    private User roomCreator;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Participant> participants = new LinkedHashSet<>();

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id desc")
    private Set<Chat> chats = new LinkedHashSet<>();

    @Column(name = "deleted_at")
    private LocalDateTime deletedTime;

    @Builder(access = AccessLevel.PRIVATE)
    private Room(String roomName, String roomContent, int limitUserCount, User user) {
        this.roomName = roomName;
        this.roomContent = roomContent;
        this.limitUserCount = limitUserCount;
        this.roomCreator = user;
    }

    public static Room of(String roomName, String roomContent, int limitUserCount, User user) {
        return Room.builder()
                .roomName(roomName)
                .roomContent(roomContent)
                .limitUserCount(limitUserCount)
                .user(user)
                .build();
    }

    public void updateRoom(String roomName, String roomContent, int limitUserCount){
        this.roomName = roomName;
        this.roomContent = roomContent;
        this.limitUserCount = limitUserCount;
    }

    public void exitRoom(Participant participantEntity){
        for(Participant participant : getParticipants()){
            if(Objects.equals(participant.getId(), participantEntity.getId())){
                getParticipants().remove(participant);
                break;
            }
        }
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
