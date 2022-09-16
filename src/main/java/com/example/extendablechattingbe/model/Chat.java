package com.example.extendablechattingbe.model;

import com.example.extendablechattingbe.model.auditing.ChatBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Chat extends ChatBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @Column
    private String message;

    @Enumerated(EnumType.STRING)
    private ChatType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public static Chat of(String message, ChatType type, User user, Room room) {
        Chat chat = new Chat();
        chat.message = message;
        chat.type = type;
        chat.user = user;
        chat.room = room;
        return chat;
    }

}
