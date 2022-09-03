package com.example.extendablechattingbe.security;

import com.example.extendablechattingbe.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private Long id;
    private String userName;
    private String password;
    private String nickname;
    private UserRole role;
    private LocalDateTime deletedAt;

    public static UserPrincipal fromDto(UserDto dto) {
        return UserPrincipal.builder()
                .id(dto.getId())
                .userName(dto.getUserName())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .role(dto.getRole())
                .deletedAt(dto.getDeletedAt())
                .build();
    }

    public UserDto toDto() {
        return UserDto.builder()
                .id(id)
                .userName(userName)
                .password(password)
                .nickname(nickname)
                .role(role)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return deletedAt == null;
    }

    @Override
    public boolean isAccountNonLocked() {
        return deletedAt == null;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return deletedAt == null;
    }

    @Override
    public boolean isEnabled() {
        return deletedAt == null;
    }

}
