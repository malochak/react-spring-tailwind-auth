package dev.mkon.server.api.security.model.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

import dev.mkon.server.api.dto.user.CreateUserDto;

public interface UserService extends UserDetailsService {

    ResponseEntity<Map<String, String>> createUser(CreateUserDto createUserDto);
}
