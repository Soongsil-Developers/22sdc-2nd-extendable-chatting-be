package com.example.extendablechattingbe.room;

import com.example.extendablechattingbe.common.code.CommonCode;
import com.example.extendablechattingbe.common.response.Response;
import com.example.extendablechattingbe.room.code.RoomCode;
import com.example.extendablechattingbe.room.dto.request.RoomCreateRequest;
import com.example.extendablechattingbe.room.dto.request.RoomUpdateRequest;
import com.example.extendablechattingbe.room.dto.response.RoomCreateResponse;
import com.example.extendablechattingbe.room.dto.response.RoomInfoDto;
import com.example.extendablechattingbe.room.dto.response.RoomParticipationCheckResponse;
import com.example.extendablechattingbe.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RoomController {
    private final RoomService roomService;

    // 방 생성
    @PostMapping("/api/rooms")
    public ResponseEntity<Response> createRoom(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                               @RequestBody @Validated RoomCreateRequest createRequest) {
        RoomCreateResponse response = roomService.saveRoom(userPrincipal.toDto(), createRequest.toDto());

        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, response));
    }

    // 방 목록 조회
    @GetMapping("/api/rooms")
    public ResponseEntity<Response> getRooms(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, roomService.getRooms(pageable)));
    }

    // 방 정보 조회
    @GetMapping("/api/rooms/{roomId}")
    public ResponseEntity<Response> getRoom(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long roomId) {
        RoomInfoDto roomInfoDto = roomService.getRoom(roomId);

        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, roomInfoDto));
    }

    // 내 채팅방 조회
    @GetMapping("/api/rooms/my")
    public ResponseEntity<Response> myRoom(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, roomService.getMyRoom(userPrincipal.toDto())));
    }

    // 해당 방 참여 여부 조회
    @GetMapping("/api/rooms/{roomId}/users")
    public ResponseEntity<Response> checkParticipation(@PathVariable Long roomId,
                                                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        boolean participate = roomService.checkParticipation(userPrincipal.toDto(), roomId);

        if (participate) {
            RoomParticipationCheckResponse response = RoomParticipationCheckResponse.builder().participating(participate).build();
            return ResponseEntity.ok(Response.of(RoomCode.PARTICIPATING_ROOM, response));
        }

        RoomParticipationCheckResponse response = RoomParticipationCheckResponse.builder().participating(participate).build();
        return ResponseEntity.ok(Response.of(RoomCode.NOT_PARTICIPATE_ROOM, response));
    }

    //방 수정 (방 참여자면 ok)
    @PutMapping("/api/rooms/{roomId}")
    public ResponseEntity<Response> modifyRoom(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                               @RequestBody RoomUpdateRequest request,
                                               @PathVariable Long roomId) {
        Response response = roomService.modifyRoom(userPrincipal.toDto(), request.toDto(roomId));

        return ResponseEntity.ok(response);

    }

    // 방 참가
    @PostMapping("/api/rooms/{roomId}/users")
    public ResponseEntity<Response> participateRoom(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                    @PathVariable Long roomId) {
        Response response = roomService.participateRoom(userPrincipal.toDto(), roomId);

        return ResponseEntity.ok(response);
    }

    // 방 나가기
    @DeleteMapping("/api/rooms/{roomId}/users")
    public ResponseEntity<Response> exitRoom(@AuthenticationPrincipal UserPrincipal userPrincipal,
                              @PathVariable Long roomId) {
        Response response = roomService.exitRoom(userPrincipal.toDto(), roomId);

        return ResponseEntity.ok(response);
    }

}
