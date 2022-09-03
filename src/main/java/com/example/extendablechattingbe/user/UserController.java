package com.example.extendablechattingbe.user;

import com.example.extendablechattingbe.common.code.CommonCode;
import com.example.extendablechattingbe.common.response.Response;
import com.example.extendablechattingbe.security.UserPrincipal;
import com.example.extendablechattingbe.user.dto.UserDto;
import com.example.extendablechattingbe.user.dto.request.UserJoinRequest;
import com.example.extendablechattingbe.user.dto.request.UserLoginRequest;
import com.example.extendablechattingbe.user.dto.response.UserJoinResponse;
import com.example.extendablechattingbe.user.dto.response.UserMyInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Response> join(@RequestBody UserJoinRequest userJoinRequest) {
        UserDto dto = userService.join(userJoinRequest.toDto());
        UserJoinResponse response = UserJoinResponse.fromDto(dto);

        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, response));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.toDto());

        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, token));
    }

    @GetMapping
    public ResponseEntity<Response> getMyInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        UserMyInfoResponse response = userService.getMyInfo(userPrincipal.getId());

        return ResponseEntity.ok(Response.of(CommonCode.GOOD_REQUEST, response));
    }

}
