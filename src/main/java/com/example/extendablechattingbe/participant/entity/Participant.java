package com.example.extendablechattingbe.participant.entity;

import com.example.extendablechattingbe.common.entity.BaseTimeEntity;
import com.example.extendablechattingbe.participant.entity.constant.Status;
import com.example.extendablechattingbe.room.entity.Room;
import com.example.extendablechattingbe.session.SocketSession;
import com.example.extendablechattingbe.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Participant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "participant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SocketSession> socketSessions = new LinkedHashSet<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Participant(Room room, User user, Status status) {
        this.room = room;
        this.user = user;
        this.status = status;
    }

    public static Participant of(Room room, User user) {
        return new Participant(room, user, Status.ONLINE);
    }

    public void online() {
        this.status = Status.ONLINE;
    }

    public void offline() {
        this.status = Status.OFFLINE;
    }

    public void checkOnOffline() {
        if (this.socketSessions.size() <= 0) {
            offline();
            return;
        }
        online();
    }

    public void sessionRemove(String sessionId) {
        for (SocketSession socketSession : getSocketSessions()) {
            if (socketSession.getSocketSessionId().equals(sessionId)) {
                getSocketSessions().remove(socketSession);
                break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
