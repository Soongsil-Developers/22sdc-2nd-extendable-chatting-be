package com.example.extendablechattingbe.user.dto.response;

import com.example.extendablechattingbe.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMyInfoResponse {
    private String nickname;
    private String userProfileImagePath;

    public static UserMyInfoResponse fromEntity(User user) {
        return UserMyInfoResponse.builder()
                .nickname(user.getNickname())
                .userProfileImagePath(user.getUserProfileImagePath())
                .build();
    }

}
