package com.example.extendablechattingbe.dto;

import com.example.extendablechattingbe.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private Long id;
    private String username;

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUserName())
                .build();
    }

}
