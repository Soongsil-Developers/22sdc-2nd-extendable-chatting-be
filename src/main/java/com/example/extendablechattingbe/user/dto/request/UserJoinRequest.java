package com.example.extendablechattingbe.user.dto.request;


import com.example.extendablechattingbe.user.dto.UserDto;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserJoinRequest {
    private String name;
    private String password;
    private String nickname;

    public UserDto toDto() {
        return UserDto.builder()
                .userName(name)
                .password(password)
                .nickname(nickname)
                .build();
    }

}
