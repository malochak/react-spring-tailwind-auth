package dev.mkon.server.api.security.model.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;

import dev.mkon.server.api.dto.user.CreateUserDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // todo - get rid of ResponseEntity
    public ResponseEntity<Map<String, String>> createUser(CreateUserDto createUserDto) {

        // todo - introduce controller advice to handle validation errors

        var validationResult = validateCreateUserDto(createUserDto);

        if (!CollectionUtils.isEmpty(validationResult)) {
            return ResponseEntity.badRequest()
                .body(validationResult);
        }

        var user = userRepository.save(new User(
            null,
            createUserDto.email(),
            passwordEncoder.encode(createUserDto.password())
        ));

        return ResponseEntity.ok().body(Map.of("id", user.getId()));
    }

    private Map<String, String> validateCreateUserDto(CreateUserDto createUserDto) {
        if (userRepository.existsByEmail(createUserDto.email())) {
            log.info("user with email '{}' already exists", createUserDto.email());
            return Map.of("message", "user already exists");
        }

        if (!createUserDto.password().equals(createUserDto.confirmPassword())) {
            log.info("Passwords for user with email '{}' don't match", createUserDto.email());
            return Map.of("message", "passwords don't match");
        }

        return Map.of();
    }
}
