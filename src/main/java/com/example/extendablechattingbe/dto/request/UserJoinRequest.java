package com.example.extendablechattingbe.dto.request;


import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserJoinRequest {
    private String name;
    private String password;
    private String nickname;

}
