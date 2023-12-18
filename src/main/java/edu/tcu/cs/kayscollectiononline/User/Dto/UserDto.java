package edu.tcu.cs.kayscollectiononline.User.Dto;

import jakarta.validation.constraints.NotEmpty;

public record UserDto(
        Long id,
        @NotEmpty(message = "username is required")
        String username,
        boolean enabled,
        @NotEmpty(message = "roles are required")
        String roles) {
}
