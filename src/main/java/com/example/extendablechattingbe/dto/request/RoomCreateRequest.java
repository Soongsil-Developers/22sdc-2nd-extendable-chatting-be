package com.example.extendablechattingbe.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomCreateRequest {

    private String name;
    private String content;
    private int limitUserCount;

}
