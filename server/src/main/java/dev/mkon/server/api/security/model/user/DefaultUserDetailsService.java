package dev.mkon.server.api.security.model.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ResponseEntity<Map<String, String>> save(String email, String password) {

        /* todo - introduce UserDTO in shared module
                and  throw exception if user already exists
                then introduce controller advice to handle it
         */

        if (userRepository.existsByEmail(email)) {
            log.info("user with email '{}' already exists", email);
            return ResponseEntity.badRequest()
                .body(Map.of("message", "user already exists"));
        }

        var user = userRepository.save(new User(null, email, password));

        return ResponseEntity.ok().body(Map.of("id", user.getId()));
    }
}
