package com.example.extendablechattingbe.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomUpdateRequest {

    private String name;
    private String content;

}
