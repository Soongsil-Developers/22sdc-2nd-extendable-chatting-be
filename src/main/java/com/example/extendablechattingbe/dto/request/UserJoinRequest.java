package com.example.extendablechattingbe.dto.request;


import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserJoinRequest {

    private String username;

}
