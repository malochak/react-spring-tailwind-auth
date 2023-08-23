package dev.mkon.server.api.dto.user;


public record CreateUserDto(
    String email,
    String password,
    String confirmPassword) {
}
