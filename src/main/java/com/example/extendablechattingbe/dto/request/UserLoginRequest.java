package com.example.extendablechattingbe.dto.request;


import lombok.*;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserLoginRequest {
    private String name;
    private String password;

}
