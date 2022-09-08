package com.example.extendablechattingbe.model;

import com.example.extendablechattingbe.room.entity.Room;
import com.example.extendablechattingbe.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Chat extends ChatBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder(access = AccessLevel.PRIVATE)
    private Chat(String message, User user, Room room) {
        this.message = message;
        this.user = user;
        this.room = room;
    }

    public static Chat of(String message, Room room, User user) {
        return Chat.builder()
                .message(message)
                .user(user)
                .room(room)
                .build();
    }

}
