package com.example.extendablechattingbe.model;

import com.example.extendablechattingbe.model.auditing.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "room_name_idx", columnList = "room_name", unique = true),
})
@Entity
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    private String roomContent;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id desc")
    private Set<Chat> chats = new LinkedHashSet<>();


    public static Room of(String roomName, String roomContent, int limitUserCount) {
        Room room = new Room();
        room.roomName = roomName;
        room.roomContent = roomContent;
        return room;
    }

}
