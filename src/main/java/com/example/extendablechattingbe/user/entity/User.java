package com.example.extendablechattingbe.user.entity;

import com.example.extendablechattingbe.common.entity.BaseTimeEntity;
import com.example.extendablechattingbe.participant.entity.Participant;
import com.example.extendablechattingbe.security.UserRole;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at is NULL")
@Table(name = "users", indexes = {
        @Index(columnList = "user_name", unique = true),
})
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(nullable = false)
    private String password;

    @Setter
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "user_profile_image_path")
    private String userProfileImagePath;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role = UserRole.USER;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private Set<Participant> participants = new LinkedHashSet<>();

    @Column(name = "deleted_at")
    private LocalDateTime deletedTime;

    @Builder(access = AccessLevel.PRIVATE)
    private User(String userName, String password, String nickname) {
        this.userName = userName;
        this.password = password;
        this.nickname = nickname;
        this.userProfileImagePath = "/images/user/default_user_profile.jpeg";
    }

    public static User of(String userName, String password, String nickname) {
        return User.builder()
                .userName(userName)
                .password(password)
                .nickname(nickname)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
