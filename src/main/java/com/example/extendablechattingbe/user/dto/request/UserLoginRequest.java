package com.example.extendablechattingbe.user.dto.request;


import com.example.extendablechattingbe.user.dto.UserDto;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserLoginRequest {
    private String name;
    private String password;

    public UserDto toDto() {
        return UserDto.builder()
                .userName(name)
                .password(password)
                .build();
    }

}
