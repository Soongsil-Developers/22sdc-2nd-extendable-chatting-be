package com.example.extendablechattingbe.user.dto;

import com.example.extendablechattingbe.security.UserPrincipal;
import com.example.extendablechattingbe.security.UserRole;
import com.example.extendablechattingbe.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String password;
    private String nickname;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .deletedAt(user.getDeletedAt())
                .build();
    }

    public User toEntity() {
        return User.of(userName, password, nickname);
    }

    public UserPrincipal toPrincipal() {
        return UserPrincipal.builder()
                .userName(userName)
                .password(password)
                .nickname(nickname)
                .role(role)
                .deletedAt(deletedAt)
                .build();
    }

}
