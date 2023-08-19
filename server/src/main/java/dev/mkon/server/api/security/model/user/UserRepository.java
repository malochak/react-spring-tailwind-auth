package dev.mkon.server.api.security.model.user;

import java.util.Optional;

interface UserRepository {

        User save(User user);

        Optional<User> findByEmail(String email);

        boolean existsByEmail(String email);
}
