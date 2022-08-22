package com.example.extendablechattingbe.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(columnDefinition = "Text")
    private String message;

    @Builder
    public Chat(Room room, String message) {
        this.room = room;
        this.message = message;
    }

}
