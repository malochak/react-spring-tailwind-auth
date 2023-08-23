package dev.mkon.server.api.web.user;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import dev.mkon.server.api.dto.user.CreateUserDto;
import dev.mkon.server.api.security.model.user.UserService;

@RequiredArgsConstructor
@RestController("/api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody CreateUserDto createUserDto) {
        return userService.createUser(createUserDto);
    }
}
