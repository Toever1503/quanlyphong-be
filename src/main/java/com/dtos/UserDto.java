package com.dtos;


import com.entities.RoleEntity;
import com.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;

    private String fullName;
    private String password;
    private String email;

    private List<String> roles;

    public static UserDto toDto(UserEntity entity) {
        if (entity == null)
            return null;
        return UserDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .fullName(entity.getFullName())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .roles(entity.getRoleSet().stream().map(RoleEntity::getRoleName).collect(java.util.stream.Collectors.toList()))
                .build();
    }
}
