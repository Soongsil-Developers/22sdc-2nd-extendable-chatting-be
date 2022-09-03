package com.example.extendablechattingbe.user;

import com.example.extendablechattingbe.security.util.JwtTokenUtils;
import com.example.extendablechattingbe.user.dto.UserDto;
import com.example.extendablechattingbe.user.dto.response.UserMyInfoResponse;
import com.example.extendablechattingbe.user.entity.User;
import com.example.extendablechattingbe.user.exception.InvalidPasswordException;
import com.example.extendablechattingbe.user.exception.UserNameDuplicatedException;
import com.example.extendablechattingbe.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expired-time-ms}")
    private Long expiredTimeMs;

    @Transactional(readOnly = true)
    public UserDto loadUserByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .map(UserDto::fromEntity)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

    public UserDto join(UserDto dto) {
        userRepository.findByUserName(dto.getUserName()).ifPresent(it -> {
            throw new UserNameDuplicatedException("중복된 아이디입니다.");
        });

        User user = userRepository.save(User.of(dto.getUserName(), encoder.encode(dto.getPassword()), dto.getNickname()));

        return UserDto.fromEntity(user);
    }

    @Transactional(readOnly = true)
    public String login(UserDto dto) {
        User user = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("유효하지 않은 비밀번호입니다.");
        }

        return JwtTokenUtils.generateToken(dto.getUserName(), secretKey, expiredTimeMs);
    }

    public UserMyInfoResponse getMyInfo(Long userId) {
        User user = findUserEntityById(userId);
        return UserMyInfoResponse.fromEntity(user);
    }

    // 유저 찾기
    private User findUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
    }

}
