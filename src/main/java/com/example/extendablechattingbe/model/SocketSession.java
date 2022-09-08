package com.example.extendablechattingbe.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SocketSession extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "socket_session_id")
    private String socketSessionId;

//    @ManyToOne
//    private Participant participant;
//
//    @Builder(access = AccessLevel.PRIVATE)
//    private SocketSession(String socketSessionId, Participant participant) {
//        this.socketSessionId = socketSessionId;
//        this.participant = participant;
//    }
//
//    public static SocketSession of(String socketSessionId, Participant participant) {
//        return SocketSession.builder()
//                .socketSessionId(socketSessionId)
//                .participant(participant)
//                .build();
//    }

}
